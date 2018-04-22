<template>
  <section class="section">
    <h1 class="title">このサイトについて</h1>
    <article class="media">
      <div class="media-content">
        <div class="content">
          <p class="subtitle">
            Qiitaでのアウトプットに基づいて、ITエンジニアの戦闘力をランキング化するサービスです。
            自分の戦闘力を知ることも可能です。それにつけてもフリーザ様の偉大さよ。
          </p>
        </div>
        <h3 class="section_title">お世話になったサイト</h3>
        <div class="content">
          <ul>
            <li>
              <a href="https://pixabay.com/" target="_blank" rel="noopener noreferrer">Pixabay</a>
            </li>
            <li>
              <a href="http://edentheworld.zero-yen.com/sentouryoku.htm" target="_blank" rel="noopener noreferrer">ＤＲＡＧＯＮ　ＢＡＬＬ  Ｚ戦士と敵の戦闘力表</a>
            </li>
          </ul>
        </div>
        <h3 class="section_title">つくった人</h3>
        <div class="content">
          <ul>
            <li>
              <a href="https://twitter.com/tmknom" target="_blank" rel="noopener noreferrer">@tmknom</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="media-right">
        <figure class="bottom-image-box">
          <p class="is-128x128 image bottom-image">
            <img src="/logo.png">
          </p>
        </figure>
      </div>
    </article>
  </section>
</template>

<style lang="scss" scoped>
  .section_title {
    margin-top: 30px;
    color: #4a4a4a;
    font-size: 1.25rem;
    font-weight: bold;
    line-height: 1.5;
  }

  .bottom-image-box {
    position: relative;
    height: 300px;
  }

  .bottom-image {
    position: absolute;
    right: 0;
    bottom: 0;
  }
</style>
