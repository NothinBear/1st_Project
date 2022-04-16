// 상태 변경 리엑트의 reducer느낌

export default {

  // MPS 등록 조회
  SET_SEARCH_CONTRACT_DETAIL_IN_MPS_AVAILABLE(state, contractDetail) {
    /*    const newArray = []
        contractDetail.gridRowJson.map(v => newArray.push(v))
        state.contractDetailList = newArray */
    state.contractDetailList = contractDetail.gridRowJson
  },

  // MPS 등록 조회
  SET_CONTRACT_DETAIL_LIST(state, contractDetail) {
    const result = state.contractDetailList.filter(list => list.contractDetailNo !== contractDetail.gridRowJson[0].contractDetailNo)
    state.contractDetailList = result
  },
}
