// Ajax通信ライブラリ
import axios from 'axios'

export default {
  request(userName) {
    const request = async (_url) => {
      const response = await axios.get(_url);
      return response.data;
    };
    const url = this.getJsonUrl(userName);
    return request(url);
  },

  getJsonUrl(userName) {
    const path = '/qiita-ranker/users/' + userName + '.json.gz';
    const baseUrl = 'http://temporary-7037dee17452.s3-website-ap-northeast-1.amazonaws.com';
    return baseUrl + path;
  }
};
