package es.ericd.twitchtop.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.ericd.twitchtop.R
import es.ericd.twitchtop.classes.Data

class StreamsAdapter(private val streamsList: MutableList<Data>, private val context: Context, private val clickItem: (stream: Data) -> Unit): RecyclerView.Adapter<StreamsAdapter.StreamsViewHolder>() {

    class StreamsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val ivWidth = 600
        private val ivHeight = 300

        private val tvStreamerName: TextView = view.findViewById(R.id.stream_streamerName)
        private val tvViewerCount: TextView = view.findViewById(R.id.stream_viewer_count)
        private val tvGame: TextView = view.findViewById(R.id.game)
        private val ivThumbnail: ImageView = view.findViewById(R.id.thumbnail)

        fun bindItem(data: Data) {
            tvStreamerName.text = data.user_name
            tvViewerCount.text = data.viewer_count.toString()
            tvGame.text = data.game_name
            Picasso.get().load(data.thumbnail_url.replace("{width}","$ivWidth").replace("{height}","$ivHeight")).into(ivThumbnail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stream_item, parent, false)

        return StreamsViewHolder(view)

    }

    override fun getItemCount(): Int {
        return streamsList.size
    }

    override fun onBindViewHolder(holder: StreamsViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickItem(streamsList[position])
        }
        holder.bindItem(streamsList[position])
    }

}