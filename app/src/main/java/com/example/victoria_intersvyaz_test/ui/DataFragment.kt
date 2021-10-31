package com.example.victoria_intersvyaz_test.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.victoria_intersvyaz_test.R
import com.example.victoria_intersvyaz_test.adapter.PhotoAdapter
import com.example.victoria_intersvyaz_test.databinding.DataFragmentBinding
import com.example.victoria_intersvyaz_test.model.Photos
import com.example.victoria_intersvyaz_test.other.Constants
import com.example.victoria_intersvyaz_test.other.Constants.NOTIFICATION_CHANNEL_ID
import com.example.victoria_intersvyaz_test.other.Constants.NOTIFICATION_ID
import com.example.victoria_intersvyaz_test.other.NotificationInstance
import com.example.victoria_intersvyaz_test.view_model.ViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DataFragment: Fragment(R.layout.data_fragment) , PhotoAdapter.ItemClickListener {
    private lateinit var binding: DataFragmentBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: PhotoAdapter

    private var arrayList: ArrayList<Photos> = ArrayList()

    private var count: Int = 0
    private var currentID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        setupUI()
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStartData()
        binding.button.setOnClickListener {
           addData()
        }
        setupData()
    }

    private fun setStartData() {
        addData()
    }

    // подгрузка 10 элементов списка
    private fun addData() {
        while (count < 10) {
            count++
            currentID++
            if(currentID < 4999) {
                viewModel.getNewComment(currentID)
            }
            else {
                Toast.makeText(context, "Данные закончились!", Toast.LENGTH_SHORT).show()
            }
        }
        count = 0
    }

    private fun setupUI() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = PhotoAdapter(arrayListOf())
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            recyclerView.adapter = adapter
        }
    }

    private fun setupData() {
        lifecycleScope.launch {
            viewModel.commentState.collect {
                when(it.status) {
                    com.example.victoria_intersvyaz_test.utils.Status.LOADING -> {
                        Toast.makeText(context, "загрузка..", Toast.LENGTH_SHORT).show()
                    }
                    com.example.victoria_intersvyaz_test.utils.Status.ERROR -> {
                        Toast.makeText(context, "ошибка!", Toast.LENGTH_SHORT).show()
                    }
                    com.example.victoria_intersvyaz_test.utils.Status.SUCCESS-> {
                        it.data?.let { it ->
                            arrayList.add(it)
                            adapter.clickListener = this@DataFragment
                            arrayList.sortWith(compareBy{ it.id?.toInt() })
                            adapter.addUsers(arrayList)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(id: String) {
        val notificationManager = getSystemService(
            requireContext(), NotificationManager::class.java) as NotificationManager

        // если уведомление запущено -> удаляем
        if(NotificationInstance.globalList[id.toInt()]) {
            // флаг = false
            NotificationInstance.globalList[id.toInt()] = false
            notificationManager.cancel(id.toInt());
        }
        // запуск уведомления
        else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_ID,
                    NOTIFICATION_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)

                val builder = NotificationCompat.Builder(requireContext(), NOTIFICATION_ID)
                    .setContentText("You get notification from $id item")
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("Notification")

                with(NotificationManagerCompat.from(requireContext())) {
                    notify(id.toInt(), builder.build())
                }
            }
            // флаг = true (запущено уведомление)
            NotificationInstance.globalList[id.toInt()] = true
        }
    }
}