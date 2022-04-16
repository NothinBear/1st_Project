export default [
  {
    title: '영업 관리',
    icon: 'TrendingUpIcon',
    children: [
      {
        title: '견적 관리',
        children: [
          {
            title: '견적 조회/수정',
            route: 'logi-page',
          },
          {
            title: '견적 등록',
            route: 'logi-page',
          },
        ],
      },
      {
        title: '수주 관리',
        children: [
          {
            title: '수주 조회',
            route: 'contractInfo',
          },
          {
            title: '수주 등록',
            route: 'registContract',
          },
        ],
      },
      {
        title: '납품 관리',
        route: 'deliveryInfoPage',
      },
      {
        title: '판매 계획',
        route: 'dashboard-analytics',
      },
      {
        title: '외주관리',
      },
    ],
  },
]
