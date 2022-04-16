import mutations from './mutation'
import actions from './action'

export default {
  namespaced: true,
  state: {
    accoundCodeList: [],
    accountCustomerList: [],
    accountControllCodeList: [],
    accountLederList: [],
    generalAccountLedgerList: [],
  },
  getters: {
    /**
         *  기존 배열에서  차변 대변 누계를 포함한 객체를 만들어 추가하는 겟터!
         *  slice = 얕은 복사본을 새로운 배열 객체로 반환합니다. 원본 배열은 바뀌지 않습니다.
         *   slice(-2) 뒤에서 부터 2개를 뽑아온다.
         * @param state
         */
    GET_ACCOUNT_LEDER_LIST(state) {
      if (state.accountLederList.length > 0) {
        const lastAccount = state.accountLederList.slice(-2)
        const object = {
          reportingDate: '누계',
          leftDebtorPrice: lastAccount[0].totalPrice,
          rightCreditsPrice: lastAccount[1].totalPrice,
        }
        return [...state.accountLederList, object]
      }
      return state.accountLederList
    },
  },

  mutations,

  actions,

}
