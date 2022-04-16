import dashboard from '@/navigation/vertical/common/dashboard'

import salesMenu from '@/navigation/vertical/logi/salesMenu'
import logisticsinfo from '@/navigation/vertical/logi/logisicsInfo'
import material from '@/navigation/vertical/logi/material'
import production from '@/navigation/vertical/logi/production'

export default [
  ...dashboard,
  {
    title: '메인페이지',
    route: 'logiHome',
    icon: 'HomeIcon',
  },
  ...logisticsinfo,
  ...salesMenu,
  ...material,
  ...production,
]
