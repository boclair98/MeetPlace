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
      return;
    }
    const [lat, lng] = select.value.split(",");
    latInput.value = Number(lat).toFixed(4);
    lngInput.value = Number(lng).toFixed(4);
  });

  removeButton.addEventListener("click", () => {
    if (participants.querySelectorAll(".participant-card").length <= 2) {
      return;
    }
    card.remove();
    reindexCards();
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

document.querySelectorAll(".participant-card").forEach(wireCard);

addButton.addEventListener("click", () => {
  const count = participants.querySelectorAll(".participant-card").length;
  if (count >= 8) {
    return;
  }
  participants.appendChild(createCard(count));
});
