<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="提交审核" />
    </el-steps>

    <el-button type="text" @click="openChapterDialog">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="openVideo(chapter.id)"
              >添加课时</el-button
            >
            <el-button style="" type="text" @click="openEditChapter(chapter.id)"
              >编辑</el-button
            >
            <el-button type="text" @click="removeChapter(chapter.id)"
              >删除</el-button
            >
          </span>
        </p>
        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <el-button type="text">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)"
                  >删除</el-button
                >
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
        >下一步</el-button
      >
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number
            v-model="chapter.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <!-- TODO -->
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API + '/eduvod/video/uploadAlyiVideo'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，<br />
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br />
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br />
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br />
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question"></i>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button
          :disabled="saveVideoBtnDisabled"
          type="primary"
          @click="saveOrUpdateVideo"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";

export default {
  name: "",
  props: [],
  components: {},
  data() {
    return {
      BASE_API: process.env.VUE_APP_BASE_API,
      fileList: [],
      saveBtnDisabled: false,
      chapterVideoList: [],
      courseId: null,
      dialogChapterFormVisible: false,
      chapter: {
        title: "",
        sort: 0,
      },

      dialogVideoFormVisible: false,
      video: {
        title: "",
        sort: 0,
        free: 0,
        videoSourceId: "",
        videoOriginalName: "",
      },
      saveVideoBtnDisabled: false,
    };
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
    }
    this.getChapterVideo();
  },

  mounted() {},

  computed: {},

  methods: {
    previous() {
      this.$router.push({ path: "/course/info/" + this.courseId });
    },
    next() {
      this.$router.push({ path: "/course/publish/" + this.courseId });
    },
    async getChapterVideo() {
      let res = await chapter.getAllChapterVideo(this.courseId);
      if (res.code == 20000) {
        this.chapterVideoList = res.data.allChapterVideo;
      }
    },
    //成功回调
    handleVodUploadSuccess(response, file, fileList) {
      this.video.videoSourceId = response.data.videoId;
      this.video.videoOriginalName = file.name;
    },
    //视图上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除${file.name}?`);
    },
    async handleVodRemove() {
      let res = await video.deleteAliyunvod(this.video.videoSourceId)
      if (res.code == 20000) {
        this.$message.success('视频删除成功')
        this.video.videoOriginalName = ''
        this.video.videoSourceId = ''
        this.fileList = []
      }
    },
    //================章节操作=========================================================
    saveOrUpdate() {
      console.log(this.chapter);
      if (this.chapter.id) {
        this.updateChapter();
      } else {
        this.addChapter();
      }
    },
    async addChapter() {
      this.chapter.courseId = this.courseId;
      let res = await chapter.addChapter(this.chapter);
      if (res.code == 20000) {
        this.dialogChapterFormVisible = false;
        this.$message.success("添加成功");
        this.getChapterVideo();
      }
    },
    async updateChapter() {
      this.chapter.courseId = this.courseId;
      let res = await chapter.updateChapter(this.chapter);
      if (res.code == 20000) {
        this.dialogChapterFormVisible = false;
        this.$message.success("修改成功");
        this.getChapterVideo();
      }
    },
    openChapterDialog() {
      this.dialogChapterFormVisible = true;
      this.chapter = {
        id: "",
        title: "",
        sort: 0,
      };
    },
    async openEditChapter(chapterId) {
      let res = await chapter.getChapterInfo(chapterId);
      if (res.code == 20000) {
        this.chapter = res.data.chapter;
        this.dialogChapterFormVisible = true;
      }
    },
    removeChapter(chapterId) {
      this.$confirm("此操作将永久删除章节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          let res = await chapter.deleteChapter(chapterId);
          if (res.code == 20000) {
            this.$message.success("删除章节成功");
            this.getChapterVideo();
          }
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    //================小节操作=================================================
    openVideo(chapterId) {
      this.video = {};
      this.dialogVideoFormVisible = true;
      this.video.chapterId = chapterId;
      this.video.courseId = this.courseId;
    },
    saveOrUpdateVideo() {
      this.addVideo();
    },
    async addVideo() {
      console.log(this.video);
      let res = await video.addVideo(this.video);
      if (res.code == 20000) {
        this.dialogVideoFormVisible = false;
        this.$message.success("添加小节成功");
        this.getChapterVideo();
      }
    },
    removeVideo(id) {
      this.$confirm("此操作将永久删除小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          let res = await video.deleteVideo(id);
          if (res.code == 20000) {
            this.$message.success("删除小节成功");
            this.getChapterVideo();
          }
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
  },
};
</script>

<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}
.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>