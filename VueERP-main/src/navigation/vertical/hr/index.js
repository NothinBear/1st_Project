export default [
    {
        title: 'insaHome',
        route: 'hrHome',
        icon: 'HomeIcon',
    },
    {
        title: '인사관리',
        icon: 'UsersIcon',
        children: [
                    {
                        title: '직원정보 관리',
                        icon: 'UserPlusIcon',
                        children: [
                            {
                                title: '직원목록조회',
                                route: 'emp-page',
                            },
                            {
                                title: '직원상세조회',
                                route: 'emp-detail'
                            },
                            {
                                title: '직원관리'
                            },
                            {
                                title: '직원등록'
                            }
                        ]
                    },
                    {
                        title: '인사고과 관리',
                        icon: 'UserCheckIcon',
                        route: 'hrHome',
                    },
                    {
                        title: '직원교육 관리',
                        icon: 'EditIcon',
                        route: 'hrHome'
            },
        ],
    },
    {
        title: '급여관리',
        icon: 'DollarSignIcon',
        children: [
            {
                title: '급여계산관리',
                icon: 'DollarSignIcon'
            },
            {
                title: '급/상여 관리',
                icon: 'DollarSignIcon'
            },
            {
                title: '연말정산관리',
                icon: 'TabletIcon'
            }
        ]
    }
]
