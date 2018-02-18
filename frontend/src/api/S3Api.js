import axios from 'axios'
import {S3_URL} from './Constant'

export default {
  request(s3Path) {
    const request = async (_url) => {
      const response = await axios.get(_url);
      return response.data;
    };

    const url = S3_URL + s3Path;
    return request(url);
  }
};
