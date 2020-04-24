import axios from "axios";
import { notification } from "ant-design-vue";

function request(options) {
  return axios(options)
    .then(res => {
      return res;
    })
    .catch(error => {
      const {
        response: { status, statusText }
      } = error;
      var text = statusText;
      if (status === 500 || status === 507) {
        text = '服务器异常'
      }
      if (status === 404 || status === 400) {
        text = '未找到数据'
      }
      notification.error({
        // eslint-disable-next-line no-unused-vars
        message:text,
        // description: status
      });
      return Promise.reject(error);
    });
}

export default request;

