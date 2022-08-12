// 전역 변수 설치

import Swal from "sweetalert2";

export const swal = (toast, position, timer, icon, title, text) => {
  const Toast = Swal.mixin({
    toast: toast,
    position: position,
    showConfirmButton: false,
    timer: timer,
    timerProgressBar: true,
  });

  Toast.fire({
    icon: icon,
    title: title,
    text: text,
  });
};
