package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapter.UserAdapter
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.models.User

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var userAdapter:UserAdapter
    private lateinit var list:ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadData()
        var mediaPlayer = MediaPlayer()
        userAdapter = UserAdapter(list, object : UserAdapter.RvAction{
            override fun itemClick(position: Int, user: User) {
                mediaList[position].start()
            }

            override fun itemLongClick(position: Int, user: User) {
                mediaList[position].pause()
            }

        })
        binding.txt1.adapter = userAdapter


        val itemTouch =object :ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val wipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags,wipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               userAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userAdapter.onItemDismiss(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(itemTouch)
            .attachToRecyclerView(binding.txt1) 
    }

    val mediaList = ArrayList<MediaPlayer>()

    private fun loadData() {
        list = ArrayList()
        for (i in 0 until 100){
            list.add(User("Shaxinbek $i", i, R.raw.izmir))
            mediaList.add(MediaPlayer())
            mediaList[i] = MediaPlayer.create(this, R.raw.izmir)
        }
    }
}