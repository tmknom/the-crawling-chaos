import S3Api from '../S3Api'

export default {
  request(userName) {
    const path = '/qiita-ranker/users/' + userName + '.json.gz';
    return S3Api.request(path);
  }
};
