document.addEventListener("DOMContentLoaded", function () {
  const tabs = document.querySelectorAll(".search-tabs a[data-tab]");
  const forms = {
    medicineName: document.getElementById("medicineName-form"),
    indication: document.getElementById("indication-form"),
    reactionTerm: document.getElementById("reactionTerm-form"),
  };

  // すべて隠す
  Object.values(forms).forEach((sec) => (sec.style.display = "none"));

  // 初期タブ取得
  let initTab = document.body.getAttribute("data-active-tab");
  // フォールバック：属性がなければ最初のキーを使う
  if (!initTab || !forms[initTab]) {
    initTab = Object.keys(forms)[0];
  }
  // 初期表示
  forms[initTab].style.display = "block";
  tabs.forEach((a) => {
    if (a.getAttribute("data-tab") === initTab) {
      a.parentElement.classList.add("active");
    } else {
      a.parentElement.classList.remove("active");
    }
  });

  // クリック時の切替
  tabs.forEach((a) => {
    a.addEventListener("click", function (e) {
      e.preventDefault();
      const key = this.getAttribute("data-tab");
      // タブの active 管理
      tabs.forEach((x) => x.parentElement.classList.remove("active"));
      this.parentElement.classList.add("active");
      // フォーム切替
      Object.entries(forms).forEach(([k, sec]) => {
        sec.style.display = k === key ? "block" : "none";
      });
    });
  });
});
