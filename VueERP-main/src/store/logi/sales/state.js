import mutations from './mutation'
import actions from './action'

export default {
  namespaced: true,
  state: {
    estimateList: [],
    grid: [],
    detailGrid: [],
    tableColumns: [],
    detailTableColumns: [],
    editEstimateDetail: [],
  },
  getters: {
  },

  mutations,

  actions,

}
