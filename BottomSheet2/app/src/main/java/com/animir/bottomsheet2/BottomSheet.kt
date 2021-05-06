package com.animir.bottomsheet2

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.animir.bottomsheet2.adapter.BottomSheetAdapter
import com.animir.bottomsheet2.databinding.LayoutBottomSheetBinding
import com.animir.bottomsheet2.vo.BottomSheetItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*
import kotlin.math.roundToInt

class BottomSheet(dataList: ArrayList<BottomSheetItem>) : BottomSheetDialogFragment(){

    companion object {
        const val TAG = "BottomSheet"
    }
    var mScreenWidth = 0F
    var mScreenHeight = 0F

    var mImageWidth = 0
    var mImageHeight = 0

    var mDataList : ArrayList<BottomSheetItem> = dataList
    var mBottomSheetBehavior : BottomSheetBehavior<View>? = null
    var mBindingView : LayoutBottomSheetBinding? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view: View = View.inflate(context, R.layout.layout_bottom_sheet, null)

        mBindingView = DataBindingUtil.bind(view)
        bottomSheet.setContentView(view)

        val displayMetrics = context!!.resources.displayMetrics
        mScreenWidth = displayMetrics.widthPixels / displayMetrics.density
        mScreenHeight = displayMetrics.heightPixels / displayMetrics.density

        val image = BitmapFactory.decodeResource(resources, R.drawable.video_img)
        val imageWidth = image.width
        val imageHeight = image.height

        mImageWidth = (pxToDP2((mScreenWidth.toInt() / 100) * 10) * imageWidth / imageHeight)
        mImageHeight = (pxToDP2((mScreenHeight.toInt() / 100) * 15) * imageHeight / imageWidth)

        bottomSheet.setOnShowListener {
            mBottomSheetBehavior = BottomSheetBehavior.from((view.parent) as View)
            mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED // 처음 열릴때 상태
            mBottomSheetBehavior?.isFitToContents = true // 아래로 드래그할때 접히는 부분 중간 여부 (false : 활성화)
            mBottomSheetBehavior?.skipCollapsed = false // 아래로 드래그해서 종료할때 접히는 구간 스킵여부
            mBottomSheetBehavior?.isHideable = false // 최소 높이로 작아질때 보여질지 여부
            mBottomSheetBehavior?.peekHeight = pxToDP2(((mScreenHeight / 100) * 16).toInt()) // 최소 높이

            mBottomSheetBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {

                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            // 드래그 동작 후 BootSheet 가 특정 높이로 고정될때
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            // 최대 높이 로 전체가 보이는 상태
//                            view.videoLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(500F))
//                            view.videoView.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            // peekHeight 길이 만큼 보이는 상태
//                            view.videoLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(70F))
//                            view.videoView.layoutParams = ConstraintLayout.LayoutParams(dpToPx(150F), LinearLayout.LayoutParams.MATCH_PARENT)
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            // 숨김 상태
                            dismiss()
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    view.videoLayout.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dpToPx(((mImageHeight * 8) * slideOffset) + mImageHeight)
                    )

                    view.videoView.layoutParams = ConstraintLayout.LayoutParams(
                        dpToPx(((mImageWidth * 8) * slideOffset) + mImageWidth),
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                    )


                    val tmp = 1.0F
                    view.title.alpha = (tmp - slideOffset)
                    view.caption.alpha = (tmp - slideOffset)
                    view.playBtn.alpha = (tmp - slideOffset)
                    view.closeBtn.alpha = (tmp - slideOffset)

                    view.title.layoutParams = LinearLayout.LayoutParams(
                        dpToPx(((mImageWidth * 4) * (tmp - slideOffset)) + mImageWidth),
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                }
            })
        }

        val bottomSheetAdapter = BottomSheetAdapter(context!!, mDataList)
        view.bottomSheetRV.adapter = bottomSheetAdapter
        view.bottomSheetRV.layoutManager = LinearLayoutManager(context)

        return bottomSheet
    }

    private fun dpToPx(dp: Float) : Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context!!.resources.displayMetrics).toInt()
    }

    private fun pxToDP(px: Int) : Int{
        return (if (context != null) {
            val resources = context!!.resources
            val metrics = resources.displayMetrics
            px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics
            px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }).toInt()
    }

    private fun pxToDP2(px: Int) : Int {
        val resources = context!!.resources
        val metrics = resources.displayMetrics
        return (px / (metrics.xdpi / metrics.densityDpi)).roundToInt()
    }


    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onStart() {
        super.onStart()
        mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hideAppBar(view: View){
        val params = view.layoutParams
        params.height = 0
        view.layoutParams = params
    }

    private fun showView(view: View, size: Int){
        val params = view.layoutParams
        params.height = size
        view.layoutParams = params
    }

    private fun getActionBarSize() : Int{
        val array = context?.theme?.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        return array?.getDimension(0, 0f)?.toInt()!!
    }


}
