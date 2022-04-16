export default {

  SEARCH_INFO(state, estimateList) {
    state.estimateList = estimateList
    console.log(`뮤테이션${JSON.stringify(state.estimateList)}`)
  },

  searchContract(state, gridRow) {
    state.detailGrid = []
    state.grid = gridRow
  },
  searchContractDetail(state, gridRow) {
    state.detailGrid = gridRow
  },
  setTable(state, tableColumns) {
    state.tableColumns = tableColumns[0].tableColumns
    state.detailTableColumns = tableColumns[0].detailTableColumns
  },
  searchEstimateInContractAvailable(state, gridRow) {
    state.detailGrid = []
    state.grid = gridRow
  },
  searchEstimateDetailInfo(state, gridRow) {
    state.detailGrid = gridRow
  },
  setEditDueDate(state, dueDate) {
    console.log(dueDate)
    if (state.editEstimateDetail === '') {
      state.editEstimateDetail = state.editEstimateDetail.concat(dueDate)
    } else {
      const isEmpty = state.editEstimateDetail.filter(e => {
        if (e.estimateNo === dueDate.estimateNo) {
          return e
        }
      })
      if (isEmpty != null) {
        console.log('isEmpty식별')
        state.editEstimateDetail.filter(e => {
          if (e.estimateNo === isEmpty.estimateNo) {
            e.dueDateOfEstimate = isEmpty.dueDateOfEstimate
          }
        })
      }
    }
    console.log(state.editEstimateDetail)
  },
}
