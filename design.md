# OpenUI Design Plan

## Variant 1

### Resolved inputs
- Topic: 여러 출발지의 중간 약속 장소 찾기
- Offer: 참가자의 출발지와 만날 업종을 정해 이동 부담을 비교하고 후보 지역 확인
- Audience: 여러 친구와 만날 중간 장소를 정하는 사용자
- Primary user action: #planner의 약속 조건 입력 후 기존 /recommend POST 실행
- Deliverable: 기존 Spring/Thymeleaf 홈페이지와 CSS; 백엔드·API 보존
- Style direction: 읽기 쉬운 로컬 약속 플래너, 기존 지도와 녹색 시각 언어 유지
- Tonal key: 기존 브랜드에 맞는 밝은 바탕
- Icon policy: 기존 기능 아이콘만 유지; 의미 없는 장식 아이콘 추가 없음
- Proof assets: none; 화면의 실제 서버 데이터만 사용
- Motion intensity: Minimal; reduced-motion 존중

### Composition contract
- Hero archetype: asymmetrical
- First-viewport structure: 짧은 두 줄 제목과 조건 입력 CTA, 다음에 실제 플래너. PC는 폼·요약 두 열, 모바일은 폼을 먼저 보여주고 요약·결과는 문서 흐름으로 배치.
- Primary visual/proof artifact: 실제 서버 기능 화면; 가짜 데이터·스크린샷 없음
- Interaction behavior: #planner의 약속 조건 입력 후 기존 /recommend POST 실행
- Reuse boundary: 모든 th: 필드와 /recommend action, 참가자 추가·삭제 ID와 데이터 속성, 결과 렌더링·외부 장소 API

### 서비스와 코드 근거
hero, planner 폼, mode/category/participants 필드, 출발지 추가·삭제, 선택 요약과 서버 렌더링 결과가 한 화면에 있다. 히어로 제목이 길고 여러 참가자 필드와 요약이 좁은 화면에서 경쟁한다.

### 디자인 기준과 구현 범위
기존 styles.css의 브랜드·바탕 토큰 우선, 본문 #17392e, 보조 #526b60, 행동 #136b4d, 경계 #d9e6de. 시스템·기존 한국어 폰트 유지. 본문 16px/1.6~1.75, 설명 15~17px, h1 PC 36~56px/mobile 28~36px/1.25, h2 22~26px/1.35. 간격 8·12·16·24·32·48·64px를 역할에 맞게 재사용하고 콘텐츠 좌우 모바일 20px, PC 32px 이상. 입력·주요 행동 최소 44px. 제목 keep-all, 긴 주소·식별자 overflow-wrap:anywhere, flex/grid 자식 min-width:0. 대비와 3px focus-visible를 보장하고 고정 높이로 본문을 자르지 않는다. 수정 경로는 output target에 한정하고 모든 th: 필드와 /recommend action, 참가자 추가·삭제 ID와 데이터 속성, 결과 렌더링·외부 장소 API를 보존한다.

### 실행과 검증 계획
Gradle 테스트·빌드, 프리셋과 참가자 추가·삭제·업종 선택·폼 검증 및 API 가능 환경에서 추천 결과까지 확인한다. API 키 부재는 분리해 보고한다. 공통 viewport는 1440×900, 768×1024, 390×844, 360×800. 실제 브라우저 이미지·가로 넘침·Tab/Enter/Space/Escape를 확인하고 확인하지 못한 항목은 미확인으로 기록한다.

### Filled generation prompt

Create one high-concept page for 여러 출발지의 중간 약속 장소 찾기.

#### Inputs
- Topic: 여러 출발지의 중간 약속 장소 찾기.
- Offer: 참가자의 출발지와 만날 업종을 정해 이동 부담을 비교하고 후보 지역 확인
- Audience: 여러 친구와 만날 중간 장소를 정하는 사용자
- Primary user action: #planner의 약속 조건 입력 후 기존 /recommend POST 실행
- Deliverable: 기존 Spring/Thymeleaf 홈페이지와 CSS; 백엔드·API 보존
- Style direction: 읽기 쉬운 로컬 약속 플래너, 기존 지도와 녹색 시각 언어 유지
- Tonal key: 기존 브랜드에 맞는 밝은 바탕
- Icon policy: 기존 기능 아이콘만 유지; 의미 없는 장식 아이콘 추가 없음
- Proof assets: none; 화면의 실제 서버 데이터만 사용
- Motion intensity: Minimal; reduced-motion 존중

#### Objective
Design one premium, memorable page with a dominant hero and one coherent visual world derived from the topic itself.
The result should feel specific, intentional, and action-oriented when the concept calls for action, not like a generic startup or SaaS template.

Choose the strongest clear solution.
Do not collapse the concept into a simpler generic pattern just to make execution easier.

- Treat single-file execution as a packaging constraint, not a creative constraint.
Do not reduce conceptual ambition, visual specificity, interaction richness, or production quality merely because the deliverable is concise.

#### Internal concept choice
Before composing, decide internally:
- one dominant hero archetype: centered / asymmetrical / immersive
- one dominant proof mode: none / embedded gesture / single artifact
- one palette driver derived from the topic
- one tonal key: light / mid / dark / topic-derived
- one motion role: none / minimal / ambient / interactive
- one icon approach: none / minimal / expressive / topic-native
- optionally one secondary accent only if it strengthens the main direction without creating a second visual world
Do not output this planning.

#### Non-negotiables
- Use a strong hero-first composition.
- Headline: 3-12 words, max 3 lines.
- Supporting copy: 1-2 short sentences, concise and specific.
- CTAs are optional. If used, include at most 1 primary and 1 secondary CTA.
- If a clear next step is central to the offer or concept, include a CTA; otherwise CTA omission is allowed.
- CTA labels must be specific to the offer and action, not generic.
- Supporting highlights are optional. If used, show them only once in a single lower support cluster beneath the hero.
- Keep the hero dominant, focused, readable, and uncluttered.
- Proof is optional. Use it only if it clearly strengthens the concept.
- If no real proof assets are provided, do not invent metrics, testimonials, customer logos, screenshots, or fake UI proof.
- Prefer depth inside the hero, proof gesture, CTA behavior, support copy, or background logic rather than adding extra sections by default.

#### Composition
- Choose the clearest structure for the topic; do not default to generic two-column marketing layouts.
- A top navigation bar is optional. When used, make it native to the chosen style direction and subordinate to the hero rather than a generic boxed header.
- The hero may be fully integrated rather than framed.
- The hero does not need to become a panel, dashboard, device mockup, rounded card, or floating showcase unless the topic genuinely demands that artifact.
- Build hierarchy through scale, contrast, spacing, alignment, depth, rhythm, and composition before using frames, dividers, or containers.
- Keep supporting content clearly subordinate to the hero.
- Do not interpret structural restraint as permission to flatten the concept into a generic minimal layout.
- Do not use text-on-text layering, typographic collisions, or overlapping live text as a compositional device.

#### Visual system
- Build one coherent visual world derived from the topic itself.
- Let the topic drive palette, contrast, typography, spacing, geometry, motifs, materials, and motion.
- Even restrained palettes must feel intentional and specific, not generic premium-neutral.
- The background must actively contribute mood, depth, and topic logic.
- If the topic suggests a real system language, physical behavior, or interface grammar, show that directly instead of generic abstract-tech decoration.
- Treat headline typography as part of the concept, not neutral text.
- The headline may use topic-derived color, selective emphasis, multi-tone treatment, or gradient accents when they strengthen the concept and remain readable.
- Do not default the headline to neutral black, white, or near-white unless that is clearly the strongest topic-native choice.

#### Background and tonal diversity
- Premium and atmospheric design may be light, mid-tone, or dark; do not default to dark.
- Choose the tonal key from the topic, offer, audience, and style direction rather than from generic cinematic or tech aesthetics.
- Contrast may come from hue, saturation, temperature, texture, density, or scale, not only darkness.

#### Proof and concept expression
- Prefer the minimum visual evidence needed to make the idea feel specific, credible, and native to the topic.
- If the concept is strongest with typography, atmosphere, and one precise gesture, do that.
- If a stronger proof artifact is justified, use one legible, unified artifact rather than multiple fragmented panels.
- Proof may take the form of a signal, trace, contour, command line, path, specimen, structural mark, scene fragment, or other topic-native evidence.
- Typography itself may carry part of the concept or proof.
- When appropriate, use one precise typographic gesture—such as selective word emphasis, chromatic offset, gradient emphasis, distortion, signal breakup, or structural interruption—to make the concept feel more specific and alive.
- Prefer one strong typographic intervention over many scattered decorative effects.
- Avoid decorative pseudo-proof.
- Avoid turning simple ideas into framed showcases, dashboard fragments, floating panels, softened slabs, or generic marketing geometry.

#### Icons and symbolic language
- Icons are optional.
- Use icons only when they improve scanability, recognition, navigation, proof clarity, or support-cluster legibility.
- Prefer topic-native icon logic over generic startup glyphs.
- Keep icon style consistent in stroke weight, geometry, corner logic, and detail level.
- Use icons primarily in clearly secondary roles unless a single topic-native symbol is central to the concept.
- Do not let icons compete with the hero headline, proof artifact, or CTA hierarchy.

#### Support cluster
- If used, show supporting highlights only once, in a single lower cluster beneath the hero.
- Each highlight should be short, fast to scan, and clearly secondary.
- Supporting highlights may use concise icons or symbolic marks when helpful.
- Support-cluster elements may use subtle motion when it improves scanability, continuity, or atmosphere and remains clearly secondary.
- Card-like grouping is allowed only inside this lower support cluster.
- These modules may use at most one light surface treatment, such as a subtle stroke, tonal fill, or very soft shadow.
- Keep support modules uniform, non-nested, and visually subordinate.
- Do not use card-like structures in the hero, proof, CTA area, or elsewhere on the page.

#### Motion
- Motion is optional.
- Use motion only when it supports hierarchy, continuity, feedback, atmosphere, or topic logic.
- Default to none, minimal, or ambient motion depending on what the concept actually needs.
- Motion may live in typography, background behavior, proof logic, support-cluster behavior, or interaction feedback.
- A single key word or short phrase may use subtle text-based motion such as glitch, flicker, jitter, scanline shift, chromatic split, text scramble, pulse, or reveal if it strengthens the concept.
- Keep motion controlled, readable, non-blocking, and subordinate to clarity.
- Avoid scrolljacking, attention-hijacking loops, and ornamental animation.
- Respect reduced-motion preferences.

#### Accessibility and readability
- Preserve strong readability and clear hierarchy at all viewport sizes.
- Maintain sufficient contrast between text and background.
- Do not place essential text over busy imagery unless readability remains strong.
- Avoid cramped spacing, visual collisions, and weak focal hierarchy.
- Do not place badges, pills, annotations, stickers, or floating interface fragments over essential text.
- If the deliverable is code, use semantic HTML structure, visible focus states, keyboard-friendly interactions, and reduced-motion handling.
- When in doubt, preserve legibility over expressive overlap.

#### Anti-drift rules
- Do not drift into bland corporate SaaS styling.
- Do not default to dashboard aesthetics, feature-grid thinking, boxed-section composition, or template-like modularity.
- Do not use disconnected motifs, random color choices, or interchangeable abstract-tech shapes.
- Do not use card-within-card composition, nested surfaces, floating panels, fake proof, or stock-looking UI.
- Keep one visual world, one dominant hero, and one clear structural logic.
- Do not allow text, labels, or interface elements to overlap, collide, or sit on top of one another.

#### Output
Return only the final 기존 Spring/Thymeleaf 홈페이지와 CSS; 백엔드·API 보존.

If the deliverable is code:
- Output a complete production-ready file or component.
- Keep dependencies minimal unless explicitly requested.
- Make it responsive.
- Keep support highlights in one lower support cluster only when used.

#### Final self-check
Before returning, verify that the result keeps:
- one dominant hero
- one coherent visual world
- no invented proof
- no cardification outside the lower support cluster
- no generic SaaS drift
- no unnecessary structural fragmentation
- no nested containment
- no floating sub-surfaces
- all text clearly legible and comfortably spaced


### Output target
- Path: src/main/resources/templates/index.html
- Path: src/main/resources/static/styles.css
