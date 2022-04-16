<template>
  <layout-vertical>

    <router-view />

    <alert-dialog
      v-if="alertDialogToggle"
      :dialog="alertDialogToggle"
      :timeout="alertDialogInfo.timeout"
      :emoji="alertDialogInfo.emoji"
      :title="alertDialogInfo.title"
      :first-line-text="alertDialogInfo.firstLineText"
    />

    <template #navbar="{ toggleVerticalMenuActive }">
      <navbar :toggle-vertical-menu-active="toggleVerticalMenuActive" />
    </template>

    <!-- <app-customizer
      v-if="showCustomizer"
      slot="customizer"
    /> -->
  </layout-vertical>
</template>

<script>
import LayoutVertical from '@core/layouts/layout-vertical/LayoutVertical.vue'
// import AppCustomizer from '@core/layouts/components/app-customizer/AppCustomizer.vue'
// import { $themeConfig } from '@themeConfig'
import { mapState } from 'vuex'
import Navbar from '../components/Navbar.vue'

export default {
  components: {
    // AppCustomizer,
    LayoutVertical,
    Navbar,
  },
  data() {
    return {
      // showCustomizer: $themeConfig.layout.customizer,
    }
  },
  computed: {
    ...mapState('common/utils', ['alertDialogToggle', 'alertDialogInfo']),
  },
}
</script>
