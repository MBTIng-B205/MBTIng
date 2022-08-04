<template>
  <el-container>
    <el-header>
      <el-row class="row">
        <el-col :span="6"
          ><el-select v-model="key" placeholder="검색키">
            <el-option value="receiveFriend" label="받은사람" />
            <el-option value="message" label="내용" /> </el-select
        ></el-col>
        <el-col :span="12">
          <el-input v-model="search" />
        </el-col>
        <el-col :span="6">
          <el-button @click="onSearch">검색</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-row class="row select">
      <el-col :span="16"></el-col>
      <el-col :span="6"
        ><el-button style="margin-right: 30px" @click="onDelete"
          >삭제</el-button
        ></el-col
      >
    </el-row>
    <table class="table">
      <thead>
        <tr>
          <th>
            <label class="form-checkbox"
              ><input type="checkbox" v-model="selectAll" @click="onSelect"
            /></label>
          </th>
          <th>받은사람</th>
          <th>내용</th>
          <th>날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr class="cell" v-for="i in tableData" :key="i" @click="onMsg(i)">
          <td>
            <label class="form-checkbox">
              <input
                type="checkbox"
                :value="i.id"
                v-model="selected"
                @click.stop
              />
              <i class="form-icon"></i>
            </label>
          </td>
          <td class="tableName">{{ i.nickname }}</td>
          <td class="tableMsg">{{ i.msg }}</td>
          <td>{{ i.date }}</td>
        </tr>
      </tbody>
    </table>
    <el-dialog v-model="dialogVisible" @close="handleClose">
      <el-header style="text-align: left; padding-top: 10px">
        <span class="to"> TO. </span>
        <span class="toFriend"> {{ toFriend }}</span>
        <img class="friendIcon" src="@/assets/friends.png" />
        <span class="toDate"> {{ toDate }} </span>
      </el-header>
      <el-input v-model="message" type="textarea" rows="10" readonly />
      <div style="margin-top: 20px">
        <el-button @click="handleClose">닫기</el-button>
      </div>
    </el-dialog>
    <div style="margin: 0 auto; margin-top: 20px">
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :page-size="10"
        :total="msgcnt"
      />
    </div>
  </el-container>
</template>

<script>
import { ref, computed } from "vue";

export default {
  setup() {
    const key = ref("");
    const search = ref("");
    const msgcnt = ref(50);
    const selected = ref([]);
    const selectAll = computed(
      () => selected.value.length === tableData.length
    );
    const toFriend = ref("");
    const toDate = ref("");
    const message = ref("");
    const dialogVisible = ref(false);
    const tableData = [
      {
        id: 1,
        nickname: "aqqqq",
        msg: "111ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd",
        date: "2022-07-31",
      },
      {
        id: 2,
        nickname: "b",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 3,
        nickname: "c",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 4,
        nickname: "d",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 5,
        nickname: "e",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 6,
        nickname: "f",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 7,
        nickname: "g",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 8,
        nickname: "h",
        msg: "111",
        date: "2022-07-31",
      },
      {
        id: 9,
        nickname: "i",
        msg: "111",
        date: "2022-07-31",
      },
    ];
    const onSearch = function () {
      if (key.value == "") {
        alert("검색키를 선택하세요");
      } else if (search.value == "") {
        alert("검색어를 입력하세요");
      } else {
        console.log("search", key.value + " " + search.value);
      }
    };

    const onSelect = function () {
      console.log(selectAll.value);
      if (!selectAll.value) {
        selected.value = [];
        for (let index in tableData) {
          selected.value.push(tableData[index].id);
        }
      } else {
        selected.value = [];
      }
    };

    const onDelete = function () {
      console.log(selected.value);
    };

    const onMsg = function (i) {
      console.log(i);
      toFriend.value = i.nickname;
      toDate.value = i.date;
      message.value = i.msg;
      dialogVisible.value = true;
    };

    const handleClose = function () {
      dialogVisible.value = false;
      message.value = "";
      toFriend.value = "";
      toDate.value = "";
    };

    return {
      key,
      selected,
      selectAll,
      search,
      msgcnt,
      dialogVisible,
      toFriend,
      toDate,
      message,
      tableData,
      onSearch,
      onSelect,
      onDelete,
      onMsg,
      handleClose,
    };
  },
};
</script>

<style>
.el-header {
  background-color: #9dd098;
}
.row {
  flex-direction: row;
  padding-top: 15px;
}
.select {
  background-color: #ececec;
  padding-bottom: 15px;
  justify-content: space-between;
}
table {
  border-top: 1px solid #ebeef5;
  border-collapse: collapse;
  table-layout: fixed;
}
th,
td {
  border-bottom: 1px solid #ebeef5;
  padding: 10px;
  text-align: left;
}
.tableName {
  max-width: 100px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.tableMsg {
  max-width: 250px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  text-align: left;
}
.cell:hover {
  background-color: #ebeef5;
}
.to {
  font-size: 30px;
  color: #cc3366;
  font-weight: bolder;
  text-shadow: 2px 4px 2px gray;
}
.toFriend {
  font-size: 20px;
  font-weight: bold;
}
.friendIcon {
  width: 25px;
  height: 25px;
  margin-left: 15px;
}
.toDate {
  float: right;
  font-size: 15px;
  padding-top: 10px;
}
.form-checkbox {
  zoom: 1.5;
}
</style>
