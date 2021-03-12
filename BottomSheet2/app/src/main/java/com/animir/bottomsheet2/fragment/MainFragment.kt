package com.animir.bottomsheet2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animir.bottomsheet2.BottomSheet
import com.animir.bottomsheet2.R
import com.animir.bottomsheet2.vo.BottomSheetItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        callBottomSheet.setOnClickListener {
            val bottomSheet = BottomSheet(getBottomSheetItems())
            bottomSheet.show(fragmentManager!!.beginTransaction(), bottomSheet.tag)
        }
    }


    private fun getBottomSheetItems() : ArrayList<BottomSheetItem>{
        val dataList = arrayListOf<BottomSheetItem>()
        dataList.add(BottomSheetItem("[test] test1 test1 test1 test1", "테스트 입니다. 1"))
        dataList.add(BottomSheetItem("[test] test2 test2 test2 test2", "테스트 입니다. 2"))
        dataList.add(BottomSheetItem("[test] test3 test3 test3 test3", "테스트 입니다. 3"))
        dataList.add(BottomSheetItem("[test] test4 test4 test4 test4", "테스트 입니다. 4"))
        return  dataList
    }

}