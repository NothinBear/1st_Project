import { searchContractDetailInMpsAvailable, convertContractDetailToMps } from '@/api/logi/production'

export default {

  // MPS등록 가능 조회
  async SEARCH_CONTRACT_DETAIL_IN_MPS_AVAILABLE({ commit }, payload) {
    try {
      const { data } = await searchContractDetailInMpsAvailable(payload)
      commit('SET_SEARCH_CONTRACT_DETAIL_IN_MPS_AVAILABLE', data.result.data)

      return data
    } catch (err) {
      throw new Error(err)
    }
  },

  // MPS 등록
  async CONVERT_CONTRACT_DETAIL_TO_MPS({ commit }, payload) {
    try {
      const { data } = await convertContractDetailToMps(payload)
      commit('SET_CONTRACT_DETAIL_LIST', data.result.data)
      return data
    } catch (err) {
      throw new Error(err)
    }
  },
}
