import {
  searchEstimateInfo, searchContract, searchContractDetail, searchEstimateInContractAvailable, searchEstimateDetailInfo, addNewContract,
} from '@/api/logi/sales'

export default {
  async searchContract({ commit }, date) {
    try {
      const res = await searchContract(date)
      const gridRow = res.data.gridRowJson
      commit('searchContract', gridRow)

      // return res
    } catch (err) {
      throw new Error(err)
    }
  },
  async SEARCH_ESTIMATE_INFO({ commit }, payload) { // 객체로 넘어오면 {}
    try {
      const { data } = await searchEstimateInfo(payload)
      console.log(JSON.stringify(data))
      console.log(data.gridRowJson[0])
      commit('SEARCH_INFO', data.gridRowJson) /* field가 맵핑하는게 배열이기 때문에 그대로 보내줌 */
      return data
    } catch (err) {
      throw new Error(err)
    }
  },

  async searchContractDetail({ commit }, contractNo) {
    console.log(contractNo)
    try {
      const res = await searchContractDetail(contractNo)
      const gridRow = res.data.gridRowJson
      commit('searchContractDetail', gridRow)

      // return res
    } catch (err) {
      throw new Error(err)
    }
  },
  setTable({ commit }, tableColumns) {
    commit('setTable', tableColumns)
  },
  async addNewContract({ commit }, param) {
    try {
      const res = await addNewContract(param)
      console.log(res)
      // return res
    } catch (err) {
      throw new Error(err)
    }
  },
  async searchEstimateInContractAvailable({ commit }, sendDate) {
    console.log(sendDate)
    try {
      const res = await searchEstimateInContractAvailable(sendDate)
      const gridRow = res.data.gridRowJson
      console.log(gridRow)
      commit('searchEstimateInContractAvailable', gridRow)

      // return res
    } catch (err) {
      throw new Error(err)
    }
  },
  async searchEstimateDetailInfo({ commit }, estimateNo) {
    try {
      const res = await searchEstimateDetailInfo(estimateNo)
      const gridRow = res.data.gridRowJson
      console.log(gridRow)
      commit('searchEstimateDetailInfo', gridRow)

      // return res
    } catch (err) {
      throw new Error(err)
    }
  },
}
