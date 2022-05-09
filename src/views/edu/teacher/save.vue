<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="teacher.sort"
          controls-position="right"
          :min="0"
        />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换头像
        </el-button>

        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API + '/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacher from "@/api/edu/teacher";
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  name: "",
  props: [],
  components: {
    ImageCropper,
    PanThumb
  },
  data() {
    return {
      teacher: {
        name: "",
        sort: 0,
        level: 1,
        career: "",
        intro: "",
        avatar: "",
      },
      imagecropperShow: false,
      imagecropperKey: 0,
      BASE_API: process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false,
    };
  },

  created() {
    if (this.$route.params.id) {
      this.getInfo(this.$route.params.id);
    }
  },

  mounted() {},

  computed: {},

  methods: {
    saveOrUpdate() {
      if (this.$route.params.id) {
        this.updateTeacher();
      } else {
        this.saveTeacher();
      }
    },

    // 添加讲师
    async saveTeacher() {
      let res = await teacher.addTeacher(this.teacher);
      if (res.code === 20000) {
        this.$message({
          type: "success",
          message: "添加成功",
        });
        this.$router.push({ path: "/teacher/table" });
      }
    },

    // 修改讲师信息
    async updateTeacher() {
      let res = await teacher.updateTeacherInfo(
        this.$route.params.id,
        this.teacher
      );
      if (res.code === 20000) {
        this.$message({
          type: "success",
          message: "修改成功",
        });
        this.$router.push({ path: "/teacher/table" });
      }
    },

    // 查询讲师信息
    async getInfo(id) {
      let res = await teacher.getTeacherInfo(id);
      if (res.code === 20000) {
        this.$message.success("信息获取成功");
        this.teacher = res.data.teacher;
      }
    },

    close () {
      this.imagecropperShow = false
      this.imagecropperKey += 1
    },

    cropSuccess (data) {
      this.imagecropperShow = false
      this.teacher.avatar = data.url
      this.imagecropperKey += 1
    },
  },
};
</script>

<style lang='less' scoped>
</style>