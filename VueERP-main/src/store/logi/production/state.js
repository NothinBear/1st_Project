import mutations from '@/store/logi/production/mutation';
import actions from '@/store/logi/production/action';
export class ContractDetailList {
    constructor() {
        this.contractDetailList = [];
    }
}
const Production = {
    namespaced: true,
    state: new ContractDetailList(),
    mutations,
    actions,
};
export default Production;
