/* eslint-disable import/prefer-default-export */

import { logiApi } from '../index'

// 전체조회
function searchContractDetailInMpsAvailable(payload) {
  return logiApi.get('/production/searchContractDetailInMpsAvailable', {
    params: {
      startDate: payload.startDate,
      endDate: payload.endDate,
      searchCondition: payload.searchCondition,
    },
  })
}

function convertContractDetailToMps(payload) {
  return logiApi.post('/production/convertContractDetailToMps', payload)
}

export { searchContractDetailInMpsAvailable, convertContractDetailToMps }
