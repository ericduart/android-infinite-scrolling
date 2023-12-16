package es.ericd.twitchtop.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.ericd.twitchtop.adapters.StreamsAdapter
import es.ericd.twitchtop.classes.Data
import es.ericd.twitchtop.databinding.FragmentStreamsBinding
import es.ericd.twitchtop.services.ApiCon
import es.ericd.twitchtop.utils.CipherUtil
import es.ericd.twitchtop.utils.EncodeUtil
import es.ericd.twitchtop.utils.InfiniteSnackbar
import es.ericd.twitchtop.utils.PreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StreamsFragment : Fragment() {

    lateinit var binding: FragmentStreamsBinding
    private var authToken: String? = null

    private var streamList = mutableListOf<Data>()

    var cursor: String? = null
    var enter = true

    var infiniteSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentStreamsBinding.inflate(inflater)

        val itemClick = {stream: Data ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitch.tv/${stream.user_login}"))
            startActivity(browserIntent)
        }

        binding.recview.adapter = StreamsAdapter(streamList, requireContext(), itemClick)
        binding.recview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val scrollListener = object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && enter) {
                    enter = false
                    getStreams()
                }

            }

        }

        binding.recview.addOnScrollListener(scrollListener)

        if (streamList.size == 0) getStreams()

        return binding.root
    }

    suspend fun getToken(): String {

        val encodedToken = PreferencesUtil.getToken(requireContext())
        val encodedIV = PreferencesUtil.getIV(requireContext())

        if (encodedToken == null || encodedIV == null) {

                val tokenResponse = ApiCon.getToken()

                Log.d("cipher","Asi -> " + tokenResponse.access_token)

                val cipherData = CipherUtil.encrypt(tokenResponse.access_token)

                val tokenEncoded = EncodeUtil.encodeDataToBase64(cipherData.cipherData)
                val ivEncoded = EncodeUtil.encodeDataToBase64(cipherData.cipherIv)

                PreferencesUtil.setToken(requireContext(), tokenEncoded)
                PreferencesUtil.setIV(requireContext(), ivEncoded)

                return tokenResponse.access_token

        } else {

            val tokenRes = CipherUtil.decrypt(
                EncodeUtil.decodeBase64toData(encodedToken),
                EncodeUtil.decodeBase64toData(encodedIV)
            )

            Log.d("cipher","que? -> " + tokenRes)

            return tokenRes
        }

    }

    fun getStreams() {

        lifecycleScope.launch(Dispatchers.IO) {

            infiniteSnackbar = InfiniteSnackbar.getInfiniteSnackbar(requireView(), "Loading")
            infiniteSnackbar?.show()

            try {

                if (authToken == null) {
                    authToken = getToken()
                }

                val streams = ApiCon.getStreams(authToken!!, cursor)

                cursor = streams.pagination.cursor
                streamList.addAll(streams.data)

                withContext(Dispatchers.Main) {
                    binding.recview.adapter?.notifyDataSetChanged()
                    enter = true
                    infiniteSnackbar?.dismiss()
                }

            } catch (e: Exception) {
                Snackbar.make(requireView(), e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }

}