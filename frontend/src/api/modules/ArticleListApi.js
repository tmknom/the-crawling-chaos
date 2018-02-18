import S3Api from '../S3Api'

export default {
  request(jsonType, index) {
    const path = '/qiita-ranker/ranking/article/article.' + jsonType + '.' + index + '.json.gz';
    return S3Api.request(path);
  }
};
