package com.taz.customapplication

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_filter_bottom_sheet.view.*


class FilterBottomSheetDialog() : BottomSheetDialogFragment() {

    enum class FilterBy(val id: Int) {
        DATE(R.id.dateChip), AMOUNT(R.id.amountChip)
    }

    enum class TradeType(val id: Int) {
        BOUGHT(R.id.boughtChip), SOLD(R.id.soldChip)
    }

    enum class Time(val id: Int) {
        TODAY(R.id.todayChip), YESTERDAY(R.id.yesterdayChip),
        LAST_WEEK(R.id.lastWeekChip), LAST_MONTH(R.id.lastMonthChip),
        MONTHS_3(R.id.Month3Chip), MONTHS_6(R.id.Month6Chip),
        YEAR_1(R.id.yearChip)
    }

    var onApply: ((
        FilterGroupSelectedItemId: Int,
        TradeTypeGroupSelectedItemId: Int,
        TimeGroupSelectedItemId: Int
    ) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.applyBtn.setOnClickListener {
            onApply?.invoke(
                view.filterChipGroup.checkedChipId,
                view.tradeTypeChipGroup.checkedChipId,
                view.timeChipGroup.checkedChipId
            )
        }
        view.resetBtn.setOnClickListener {
            view.filterChipGroup.check(FilterBy.DATE.id)
            view.tradeTypeChipGroup.check(TradeType.BOUGHT.id)
            view.timeChipGroup.check(Time.TODAY.id)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false)
    }

}