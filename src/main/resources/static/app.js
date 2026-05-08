const presets = [
  { name: "홍대입구", lat: 37.5572, lng: 126.9245 },
  { name: "신촌", lat: 37.5559, lng: 126.9368 },
  { name: "서울역", lat: 37.5547, lng: 126.9706 },
  { name: "종각", lat: 37.5702, lng: 126.9830 },
  { name: "을지로입구", lat: 37.5660, lng: 126.9826 },
  { name: "강남역", lat: 37.4979, lng: 127.0276 },
  { name: "잠실", lat: 37.5133, lng: 127.1002 },
  { name: "건대입구", lat: 37.5404, lng: 127.0692 },
  { name: "사당", lat: 37.4765, lng: 126.9816 },
  { name: "왕십리", lat: 37.5612, lng: 127.0371 },
  { name: "수원역", lat: 37.2657, lng: 127.0001 },
  { name: "부천역", lat: 37.4840, lng: 126.7827 }
];

const participants = document.querySelector("#participants");
const addButton = document.querySelector("#addParticipant");
const participantCount = document.querySelector("#participantCount");
const categoryCount = document.querySelector("#categoryCount");
const selectedCategories = document.querySelector("#selectedCategories");
const participantSummary = document.querySelector("#participantSummary");
const bundleButtons = document.querySelectorAll("[data-bundle]");
const sharePanel = document.querySelector("#sharePanel");
const shareText = document.querySelector("#shareText");
const copyShare = document.querySelector("#copyShare");

const categoryBundles = {
  meal: ["RESTAURANT", "CAFE"],
  play: ["PC_ROOM", "KARAOKE", "BOWLING"],
  quiet: ["CAFE", "STUDY_ROOM"],
  all: ["CAFE", "RESTAURANT", "PC_ROOM", "KARAOKE", "PUB", "STUDY_ROOM", "MOVIE", "BOWLING"]
};

function hydratePresetSelect(select) {
  if (select.dataset.ready) {
    return;
  }

  presets.forEach((preset) => {
    const option = document.createElement("option");
    option.value = `${preset.lat},${preset.lng}`;
    option.textContent = preset.name;
    select.appendChild(option);
  });

  select.dataset.ready = "true";
}

function wireCard(card) {
  const select = card.querySelector(".preset-select");
  const latInput = card.querySelector(".latitude-input");
  const lngInput = card.querySelector(".longitude-input");
  const removeButton = card.querySelector(".remove-button");

  hydratePresetSelect(select);

  select.addEventListener("change", () => {
    if (!select.value) {
      updateSummary();
      return;
    }
    const [lat, lng] = select.value.split(",");
    latInput.value = Number(lat).toFixed(4);
    lngInput.value = Number(lng).toFixed(4);
    updateSummary();
  });

  card.querySelectorAll("input").forEach((input) => {
    input.addEventListener("input", updateSummary);
  });

  removeButton.addEventListener("click", () => {
    if (participants.querySelectorAll(".participant-card").length <= 2) {
      return;
    }
    card.remove();
    reindexCards();
    updateSummary();
  });
}

function reindexCards() {
  participants.querySelectorAll(".participant-card").forEach((card, index) => {
    card.querySelector(".participant-top strong").textContent = `참가자 ${index + 1}`;
    card.querySelectorAll("input").forEach((input) => {
      input.name = input.name.replace(/participants\[\d+]/, `participants[${index}]`);
      input.id = input.id.replace(/participants\d+/, `participants${index}`);
    });
  });
}

function updateSummary() {
  const participantCards = Array.from(participants.querySelectorAll(".participant-card"));
  const checkedCategories = Array.from(document.querySelectorAll(".category-option input:checked"));

  participantCount.textContent = participantCards.length;
  categoryCount.textContent = checkedCategories.length;

  selectedCategories.textContent = checkedCategories.length
    ? checkedCategories.map((input) => input.dataset.label || input.value).join(", ")
    : "카테고리를 선택하면 여기에 표시됩니다.";

  participantSummary.textContent = participantCards
    .map((card) => {
      const name = card.querySelector("input[name$='.name']").value || "이름 없음";
      const preset = card.querySelector(".preset-select");
      const selectedPlace = preset.options[preset.selectedIndex]?.textContent;
      const lat = card.querySelector(".latitude-input").value;
      const lng = card.querySelector(".longitude-input").value;

      if (selectedPlace && selectedPlace !== "직접 입력") {
        return `${name}: ${selectedPlace}`;
      }
      return `${name}: ${Number(lat).toFixed(3)}, ${Number(lng).toFixed(3)}`;
    })
    .join(" / ");
}

function createCard(index) {
  const card = document.createElement("div");
  card.className = "participant-card";
  card.innerHTML = `
    <div class="participant-top">
      <strong>참가자 ${index + 1}</strong>
      <button class="remove-button" type="button">삭제</button>
    </div>
    <label>
      <span>이름</span>
      <input type="text" name="participants[${index}].name" value="참가자 ${index + 1}">
    </label>
    <label>
      <span>출발 지역</span>
      <select class="preset-select">
        <option value="">직접 입력</option>
      </select>
    </label>
    <div class="coord-grid">
      <label>
        <span>위도</span>
        <input class="latitude-input" type="number" step="0.0001" name="participants[${index}].latitude" value="37.5702">
      </label>
      <label>
        <span>경도</span>
        <input class="longitude-input" type="number" step="0.0001" name="participants[${index}].longitude" value="126.9830">
      </label>
    </div>
  `;
  wireCard(card);
  return card;
}

function wireResultTabs() {
  const tabs = document.querySelectorAll("[data-result-tab]");
  const panels = document.querySelectorAll("[data-result-panel]");

  tabs.forEach((tab) => {
    tab.addEventListener("click", () => {
      tabs.forEach((item) => item.classList.remove("active"));
      panels.forEach((panel) => panel.classList.remove("active"));
      tab.classList.add("active");
      document.querySelector(`[data-result-panel="${tab.dataset.resultTab}"]`)?.classList.add("active");
    });
  });
}

function hydrateDistanceBars() {
  document.querySelectorAll(".recommendation-card").forEach((card) => {
    const rows = Array.from(card.querySelectorAll(".distance-row"));
    const max = Math.max(...rows.map((row) => Number(row.dataset.distance || 0)), 1);

    rows.forEach((row) => {
      const distance = Number(row.dataset.distance || 0);
      const fill = row.querySelector("em");
      fill.style.width = `${Math.max((distance / max) * 100, 8)}%`;
    });
  });
}

function wireChooseButtons() {
  document.querySelectorAll(".choose-button").forEach((button) => {
    button.addEventListener("click", () => {
      const card = button.closest(".recommendation-card");
      const reviewText = Number(card.dataset.reviewCount || 0) > 0
        ? ` 평점 ${card.dataset.rating}, 리뷰 ${card.dataset.reviewCount}개도 확인됐어요.`
        : "";
      const mapText = card.dataset.placeUrl ? ` ${card.dataset.placeUrl}` : "";
      const text = `MeetPlace 추천: ${card.dataset.categoryName} 약속은 ${card.dataset.placeName} 어때요? 평균 이동거리 ${card.dataset.averageDistance}km, 예상 ${card.dataset.travelMinutes}분, 추천점수 ${card.dataset.score}점입니다.${reviewText}${mapText}`;

      document.querySelectorAll(".recommendation-card").forEach((item) => item.classList.remove("selected"));
      card.classList.add("selected");

      sharePanel.classList.add("ready");
      sharePanel.querySelector("b").textContent = `${card.dataset.placeName} 선택됨`;
      shareText.textContent = text;
      copyShare.disabled = false;
    });
  });
}

function wireCopyButton() {
  if (!copyShare) {
    return;
  }

  copyShare.addEventListener("click", async () => {
    try {
      await navigator.clipboard.writeText(shareText.textContent);
      copyShare.textContent = "복사 완료";
      setTimeout(() => {
        copyShare.textContent = "공유 문구 복사";
      }, 1500);
    } catch (error) {
      shareText.focus();
      copyShare.textContent = "직접 복사해 주세요";
    }
  });
}

document.querySelectorAll(".participant-card").forEach(wireCard);
document.querySelectorAll(".category-option input").forEach((input) => {
  input.addEventListener("change", updateSummary);
});
bundleButtons.forEach((button) => {
  button.addEventListener("click", () => {
    const selectedValues = categoryBundles[button.dataset.bundle] || [];
    document.querySelectorAll(".category-option input").forEach((input) => {
      input.checked = selectedValues.includes(input.value);
    });
    updateSummary();
  });
});

if (addButton) {
  addButton.addEventListener("click", () => {
    const count = participants.querySelectorAll(".participant-card").length;
    if (count >= 8) {
      return;
    }
    participants.appendChild(createCard(count));
    updateSummary();
  });
}

updateSummary();
wireResultTabs();
hydrateDistanceBars();
wireChooseButtons();
wireCopyButton();