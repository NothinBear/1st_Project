import Vuex, { Module } from 'vuex'
import mutations from '@/store/logi/production/mutation'
import actions from '@/store/logi/production/action'

export class ContractDetailList {
    public contractDetailList: Object[] = [];
}

const Production: Module<ContractDetailList, Object> = {
  namespaced: true,
  state: new ContractDetailList(),
  mutations,
  actions,
}

export default Production
