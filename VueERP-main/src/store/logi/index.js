import sales from './sales/state'
import production from './production/state'

export default {
  namespaced: true,
  state: {
    connect: false,
  },
  getters: {
  },
  actions: {
  },
  mutations: {
  },
  modules: {
    sales, production,
  },
}
