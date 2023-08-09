package com.project.chatgpt

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.project.chatgpt.Adapters.FragmentAdapter
import com.project.chatgpt.Fragments.ChatFragment
import com.project.chatgpt.Fragments.FindFriends
import com.project.chatgpt.Fragments.FriendReq
import com.project.chatgpt.Utils.AppUtility
import com.project.chatgpt.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding
    lateinit var adapter:FragmentAdapter
    var flist=ArrayList<Fragment>()

    val list= listOf("Chat","Find Friends","Requests")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil. setContentView(this,R.layout.activity_home)
        val window = this.window
        window.statusBarColor = Color.parseColor("#070B17")

        flist.add(ChatFragment(this@Home))
        flist.add(FindFriends(this@Home))
        flist.add(FriendReq(this@Home))

        adapter=FragmentAdapter(this,flist)
        binding.pager.adapter=adapter

        TabLayoutMediator(binding.lytab,binding.pager){ tab, pos ->
            tab.text=list[pos]
        }.attach()
        binding.lytab.setBackgroundColor(Color.parseColor("#070B17"))
        binding.srchbar.setBackgroundColor(Color.parseColor("#070B17"))

        binding.lytab.addOnTabSelectedListener(object :OnTabSelectedListener{
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position==0){
                    binding.srchbar.setBackgroundColor(Color.parseColor("#070B17"))
                    binding.lytab.setBackgroundColor(Color.parseColor("#070B17"))
                    binding.lytab.setTabTextColors(resources.getColor(R.color.purple_700,null),resources.getColor(R.color.white,null))
                    window.statusBarColor = Color.parseColor("#070B17")
                    window.decorView.systemUiVisibility= View.STATUS_BAR_VISIBLE
                }else{
                    binding.lytab.setBackgroundColor(Color.WHITE)
                    binding.srchbar.setBackgroundColor(Color.WHITE)
                    binding.lytab.setTabTextColors(resources.getColor(R.color.purple_700,null),resources.getColor(R.color.black,null))
                    window.statusBarColor =Color.WHITE
                    window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.btmenu.setOnClickListener {
//            var wrapper: Context =  ContextThemeWrapper(this, R.style.CustomPopUpStyle)
            val pop= PopupMenu(this,binding.btmenu)
            pop.menuInflater.inflate(R.menu.popup2,pop.menu)
            pop.show()
        }
    }
    fun ChangeTab(){
        binding.lytab.selectTab(binding.lytab.getTabAt(0))
        AppUtility.SnacView("Friend Added",binding.lytab)
    }

}