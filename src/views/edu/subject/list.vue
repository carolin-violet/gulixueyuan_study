<template>
  <div class="app-container">
    <el-input
      v-model="filterText"
      placeholder="Filter keyword"
      style="margin-bottom: 30px"
    />
    <el-tree
      ref="subjectTree"
      :data="subjectList"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />
  </div>
</template>

<script>
import subject from "@/api/edu/subject";

export default {
  name: "",
  props: [],
  components: {},
  data() {
    return {
      filterText: "",
      subjectList: [],
      defaultProps: {
        children: "children",
        label: "title",
      },
    };
  },

  created () {
    this.fetchNodeList()
  },

  mounted() {},

  watch: {
    filterText(val) {
      this.$refs.subjectTree.filter(val);
    },
  },

  computed: {},

  methods: {
    async fetchNodeList() {
      let res = await subject.getSubjectList()
      this.subjectList = res.data.list
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1;
    },
  },
};
</script>

<style lang='less' scoped>
</style>