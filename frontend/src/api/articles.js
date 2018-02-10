// Ajax通信ライブラリ
import axios from 'axios';

export default {

  request(jsonType) {
    const request = async (_url) => {
      const response = await axios.get(_url);
      return response.data;
    };
    const url = this.getJsonUrl(jsonType);
    return request(url);
  },

  getJsonUrl(type) {
    const path = '/qiita-ranker/article/article.' + type + '.json.gz';
    const baseUrl = 'http://temporary-7037dee17452.s3-website-ap-northeast-1.amazonaws.com';
    return baseUrl + path;
  }
};
