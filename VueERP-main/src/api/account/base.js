import { accountApi } from '@/api'

const ACCOUNT_URL='/base/accounts'
const ACCOUNT_CODE_URL='/base/accountCodes'

/**
 * 계정코드조회
 * @param searchCode
 * @returns {*}
 */
function fetchAccountCode(searchCode) {
    console.log(searchCode)
    const { accountCode, accountName } = searchCode
    return accountApi.get(`${ACCOUNT_CODE_URL}`,
        {
            params: {
                accountCode,
                accountName,
            },
        })
}
/**
 * 계정별원장
 */
function fetchAccountLedger(searchCode){
    console.log(searchCode)
    const { accountCode, startDate, endDate } = searchCode
    return accountApi.get(`${ACCOUNT_URL}/${accountCode}`,
        {
            params: {
                startDate,
                endDate,
            },
        })
}

/**
 * 총계정원장
 */
function fetchGeneralAccountLedger(searchCode){
    const {startDate, endDate } = searchCode
    return accountApi.get(`${ACCOUNT_URL}/`,
        {
            params: {
                startDate,
                endDate,
            },
        })
}

/**
 * 고객사조회
 * @returns {*}
 */
function fetchAccountCustomerCode() {
    return accountApi.get('/base/customers')
}
/**
 * 컨트롤 코드조회
 * @returns {*}
 */
function fetchAccountControllCode() {
    return accountApi.get('/base/accountControllerCodes')
}



export { fetchAccountCode, fetchAccountCustomerCode, fetchAccountControllCode,fetchAccountLedger ,fetchGeneralAccountLedger}
