import Vue from 'vue'
import Vuex from 'vuex'

// Modules
import router from '@/router'
import app from './app'
import appConfig from './app-config'
import verticalMenu from './vertical-menu'
import account from './account'
import hr from './hr'
import logi from './logi'
import common from './common'
import auth from './auth'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    appConfig,
    verticalMenu,
    account,
    hr,
    logi,
    auth,
    common,
  },
  mutations: {
    changeTab(state, payload) {
      console.log(payload)
      if (payload === 'hr') {
        state.account.connect = false
        state.logi.connect = false
        state.hr.connect = true
        router.push({ name: 'hrHome' })
      } else if (payload === 'account') {
        state.account.connect = true
        state.logi.connect = false
        state.hr.connect = false
        router.push({ name: 'accountHome' })
      } else if (payload === 'logi') {
        state.account.connect = false
        state.logi.connect = true
        state.hr.connect = false
        router.push({ name: 'logiHome' })
      }
    },
  },
  strict: process.env.DEV,
})
