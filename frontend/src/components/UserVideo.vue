<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
// import { reactive } from "@vue/reactivity";
import OvVideo from "./OvVideo.vue";
import { computed } from "@vue/runtime-core";
export default {
  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },

  setup(props) {
    // const state = reactive({
    //   clientData: computed(() => {
    //     getConnectionData();
    //   }),
    // });
    const clientData = computed(() => {
      const { clientData } = getConnectionData();
      return clientData;
    });

    const getConnectionData = () => {
      const { connection } = props.streamManager.stream;
      // console.log(connection, "<---------------");
      return JSON.parse(connection.data);
    };
    return {
      // state,
      getConnectionData,
      clientData,
    };
  },
};
</script>
