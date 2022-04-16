import {
  fetchAccountCode,
  fetchAccountCustomerCode,
  fetchAccountControllCode,
  fetchAccountLedger,
  fetchGeneralAccountLedger,
} from '@/api/account/base'

export default {
  // 해당 계정과목 찾기
  async FETCH_ACCOUNT_CODE({ commit }, searchCode) { // 객체로 넘어오면 {}
    try {
      const { data } = await fetchAccountCode(searchCode)
      commit('SET_ACCOUNTCODE_LIST', data.accoundCodeList)
      return data
    } catch (err) {
      throw new Error(err)
    }
  },

  // 고객사 찾기
  async FETCH_ACCOUNT_CUSTOMER_CODE({ commit }) { // 객체로 넘어오면 {}
    try {
      const { data } = await fetchAccountCustomerCode()
      console.log(data)
      commit('SET_ACCOUNT_CUSTOMER_LIST', data.accountCustomerList)
      return data
    } catch (err) {
      throw new Error(err)
    }
  },
  // 분개상세 코드 찾기
  async FETCH_ACCOUNT_CONTROLL_CODE({ commit }) { // 객체로 넘어오면 {}
    try {
      const { data } = await fetchAccountControllCode()
      console.log(data)
      commit('SET_ACCOUNT_CONTROLL_CODE_LIST', data.accountControllCodeList)
      return data
    } catch (err) {
      throw new Error(err)
    }
  },
  /**
     * 계정별원장
     */
  async FETCH_ACCOUNT_LEDGER({ commit }, searchData) {
    try {
      const response = await fetchAccountLedger(searchData)
      console.log(response)
      commit('SET_ACCOUNT_LEDGER_LIST', response.data.accountLederList)
      return response
    } catch (err) {
      throw new Error(err)
    }
  },
  /**
     * 총계정원장
     */
  async FETCH_GENERAL_ACCOUNT_LEDGER({ commit }, searchData) {
    try {
      const response = await fetchGeneralAccountLedger(searchData)
      console.log(response)
      commit('SET_GENERAL_ACCOUNT_LEDGER', response.data)
      return response
    } catch (err) {
      throw new Error(err)
    }
  },

}
