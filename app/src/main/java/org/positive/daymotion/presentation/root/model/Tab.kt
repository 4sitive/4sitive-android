package org.positive.daymotion.presentation.root.model

import androidx.fragment.app.Fragment
import org.positive.daymotion.presentation.category.fragment.CategoryTabFragment
import org.positive.daymotion.presentation.home.HomeTabFragment
import org.positive.daymotion.presentation.my.fragment.MyTabFragment
import org.positive.daymotion.presentation.setting.SettingTabFragment
import kotlin.reflect.KClass

enum class Tab(val clazz: KClass<out Fragment>) {

    HOME(HomeTabFragment::class),

    CATEGORY(CategoryTabFragment::class),

    MY(MyTabFragment::class),

    SETTING(SettingTabFragment::class)
}