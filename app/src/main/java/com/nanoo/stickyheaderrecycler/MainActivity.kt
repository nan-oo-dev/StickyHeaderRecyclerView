package com.nanoo.stickyheaderrecycler

//import com.nanoo.stickyheaderrecyclerview.StickyRecyclerHeadersAdapter
//import com.nanoo.stickyheaderrecyclerview.StickyRecyclerHeadersDecoration
//import com.nanoo.stickyheaderrecyclerview.StickyRecyclerHeadersTouchListener
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nanoo.stickyheaderrecycler.databinding.ActivityMainBinding
import com.nanoo.stickyheaderrecycler.databinding.ViewHeaderBinding
import com.nanoo.stickyheaderrecycler.databinding.ViewItemBinding
import com.nanoo.stickyheaderrecyclerview.StickyRecyclerHeadersAdapter
import com.nanoo.stickyheaderrecyclerview.StickyRecyclerHeadersDecoration
import com.nanoo.stickyheaderrecyclerview.StickyRecyclerHeadersTouchListener
import java.security.SecureRandom

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        val button = findViewById<View>(R.id.button_update) as Button
        val isReverseButton = findViewById<View>(R.id.button_is_reverse) as ToggleButton

        // Set adapter populated with example dummy data
        val adapter = AnimalsHeadersAdapter()
        adapter.add("Animals below!")
        adapter.addAll(*dummyDataSet)
        binding.recyclerview.adapter = adapter

//         Set button to update all views one after another (Test for the "Dance")
        binding.buttonUpdate.setOnClickListener {
            val handler = Handler(Looper.getMainLooper())
            for (i in 0 until adapter.itemCount) {
                val index: Int = i
                handler.postDelayed({ adapter.notifyItemChanged(index) }, 50)
            }
        }

        // Set layout manager
        val orientation = getLayoutManagerOrientation(resources.configuration.orientation)
        val layoutManager =
            LinearLayoutManager(this, orientation, binding.buttonIsReverse.isChecked)
        binding.recyclerview.layoutManager = layoutManager

        // Add the sticky headers decoration
        val headersDecor = StickyRecyclerHeadersDecoration(adapter)
        binding.recyclerview.addItemDecoration(headersDecor)

        // Add decoration for dividers between list items
        binding.recyclerview.addItemDecoration(DividerDecoration(this))

        // Add touch listeners
        val touchListener =
            StickyRecyclerHeadersTouchListener(binding.recyclerview, headersDecor)
        touchListener.setOnHeaderClickListener(
            object : StickyRecyclerHeadersTouchListener.OnHeaderClickListener {
                override fun onHeaderClick(header: View?, position: Int, headerId: Long) {
                    Toast.makeText(
                        this@MainActivity, "Header position: $position, id: $headerId",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        binding.recyclerview.addOnItemTouchListener(touchListener)
        binding.recyclerview.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        adapter.remove(adapter.getItem(position))
                    }
                })
        )
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                headersDecor.invalidateHeaders()
            }
        })

        binding.buttonIsReverse.setOnClickListener {
            val isChecked = binding.buttonIsReverse.isChecked
            binding.buttonIsReverse.isChecked = isChecked
            layoutManager.setReverseLayout(isChecked)
            adapter.notifyDataSetChanged()
        }

    }

    private val dummyDataSet: Array<String>
        get() = resources.getStringArray(R.array.animals)

    private fun getLayoutManagerOrientation(activityOrientation: Int): Int {
        return if (activityOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            LinearLayoutManager.VERTICAL
        } else {
            LinearLayoutManager.HORIZONTAL
        }
    }

    private inner class AnimalsHeadersAdapter : AnimalsAdapter<RecyclerView.ViewHolder>(),
        StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return object : RecyclerView.ViewHolder(view.root) {
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val textView = holder.itemView as TextView
            textView.text = getItem(position)
        }

        override fun getHeaderId(position: Int): Long {
            return if (position == 0) {
                -1
            } else {
                getItem(position)[0].code.toLong()
            }
        }

        override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.view_header, parent, false)
            val view = ViewHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return object : RecyclerView.ViewHolder(view.root) {
            }
        }

        override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val textView = holder.itemView as TextView
            textView.text = getItem(position)[0].toString()
            holder.itemView.setBackgroundColor(randomColor)
        }

        private val randomColor: Int
            get() {
                val rgen = SecureRandom()
                return Color.HSVToColor(
                    150, floatArrayOf(
                        rgen.nextInt(359).toFloat(), 1f, 1f
                    )
                )
            }
    }
}
