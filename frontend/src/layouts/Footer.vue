<template>
  <footer class="footer">
    <div class="container">
      <div class="content has-text-centered">

        <!--https://qiita.com/hkusu/items/fda8d8178dd693f95f3c-->
        <span class="icon has-text-info">
          <i class="fas fa-info-circle"></i>
        </span>
        <span class="fa-stack text-qiita">
          <i class="fa fa-square fa-stack-2x"></i>
          <i class="fa fa-stack-1x fa-inverse fa-search fa-2x"></i>
        </span>
        <span class="fa-stack text-twitter">
          <i class="fas fa-square fa-stack-2x"></i>
          <i class="fab fa-stack-1x fa-inverse fa-twitter"></i>
          </span>
        <span class="fa-stack text-facebook">
          <i class="fas fa-square fa-stack-2x"></i>
          <i class="fab fa-stack-1x fa-inverse fa-facebook"></i>
        </span>
        <span class="fa-stack text-get-pocket">
          <i class="fas fa-square fa-stack-2x"></i>
          <i class="fab fa-stack-1x fa-inverse fa-get-pocket"></i>
        </span>

        <p>
          <strong>Bulma</strong> by <a href="https://jgthms.com">Jeremy Thomas</a>. The source code is licensed
          <a href="http://opensource.org/licenses/mit-license.php">MIT</a>. The website content
          is licensed <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY NC SA 4.0</a>.
        </p>
      </div>
    </div>
  </footer>
</template>

<style lang="scss" scoped>
  .text-qiita {
    color: #4cb10d;
  }

  .text-twitter {
    color: #55acee;
  }

  .text-facebook {
    color: #3b5998;
  }

  .text-get-pocket {
    color: #ee4256;
  }
</style>
