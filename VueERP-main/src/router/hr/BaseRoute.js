const ERP = '/hr'
const insaRouter = [
  {
    path: `${ERP}/home`,
    name: 'hrHome',
    component: () => import('@/views/hr/Home.vue'),
    meta: {
      pageTitle: 'HR',
      breadcrumb: [
        {
          text: 'HR Home',
          active: true,
        },
      ],
    },
  },
  {
    path: `${ERP}/emp-page`,
    name: 'emp-page',
    component: () => import('@/views/hr/affair/page/EmpDetailed/EmpListGrid.vue'),
    meta: {
      pageTitle: '직원정보 관리',
      breadcrumb: [
        {
          text: '인사관리',
        },
        {
          text: '직원정보 관리',
        },
        {
          text: '직원 목록조회',
          active: true,
        },
      ],
    },
  },
  {
    path: `${ERP}/emp-detail`,
    name: 'emp-detail',
    component: () => import('@/views/hr/affair/page/EmpDetailed/EmpDetail.vue'),
    meta: {
      pageTitle: '직원 상세정보',
      breadcrumb: [
        {
          text: '인사관리',
        },
        {
          text: '직원정보 관리',
        },
        {
          text: '직원 목록조회',
          to : `${ERP}/emp-page/`
        },
        {
          text: '직원 상세조회',
          active: true,
        },
      ],
    },
  },
  {
    path: '/hr-info',
    name: 'hr-info',
    component: () => import('@/views/SecondPage.vue'),
    meta: {
      pageTitle: 'Second Page',
      breadcrumb: [
        {
          text: 'Second Page',
          active: true,
        },
      ],
    },
  },
]
export default insaRouter
