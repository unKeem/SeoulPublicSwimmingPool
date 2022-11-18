package com.example.seoulpublicswimmingpool

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seoulpublicswimmingpool.data.SwimmingPool
import com.example.seoulpublicswimmingpool.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val dataList = mutableListOf<DataVO>()
    lateinit var customAdapter: CustomAdapter

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1.retrofit 객체를 생성
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 2.retrofit 객체에 interface를 전달
        val service = retrofit.create(SeoulOpenService::class.java)

        // 3.인터페이스 함수를 오버라이딩 해서 구현
        service.getSwimmingPools(SeoulOpenApi.API_KEY, SeoulOpenApi.LIMIT)
            .enqueue(object : Callback<SwimmingPool> {
                override fun onResponse(
                    call: Call<SwimmingPool>,
                    response: Response<SwimmingPool>
                ) {
                    val data = response.body()

                    data?.let {
//                        it.SeoulPublicLibraryInfo.list_total_count
                        Log.d("retrofit", "서울공공수영장 강습 내용 로드 성공")
                        for (loadData in it.ListProgramByPublicSportsFacilitiesService.row) {
                            val center_name = loadData.CENTER_NAME
                            val week = loadData.WEEK
                            val class_time = loadData.CLASS_TIME
                            val fee = loadData.FEE
                            Log.d("retrofit", "${center_name} ${week} ${class_time} ${fee}")
                        }
                    } ?: let {
                        Log.d("retrofit", "서울공공수영장 강습 데이터 없음")
                    }
                }

                override fun onFailure(call: Call<SwimmingPool>, t: Throwable) {
                    Log.d("retrofit", "공공도서관 로딩 에러 ${t.printStackTrace()}")
                    Toast.makeText(this@MainActivity, "서울공공수영장 강습 데이터 로딩 에러", Toast.LENGTH_SHORT)
                        .show()
                }
            })

//        val cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            arrayOf<String>(ContactsContract.CommonDataKinds.Phone.Co,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                ContactsContract.CommonDataKinds.Phone.NUMBER),
//            null,
//            null,
//            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
//        )
//        while(cursor?.moveToNext()?:false){
//            val id = cursor?.getString(0)
//            val name = cursor?.getString(1)
//            val phone = cursor?.getString(2)
//            Log.d("phoneaddress", "${id}, ${name}, ${phone}")
//            dataList.add(DataVO(id!!, name!!, phone!!))
//        }

        val layoutManager: LinearLayoutManager = selectLayoutManager(1)
        customAdapter = CustomAdapter(dataList)
        this.customAdapter = customAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = customAdapter
        binding.recyclerView.setHasFixedSize(true)

    }

    private fun selectLayoutManager(i: Int): LinearLayoutManager {
        return LinearLayoutManager(this)
    }

}