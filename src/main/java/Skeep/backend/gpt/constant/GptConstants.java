package Skeep.backend.gpt.constant;

public class GptConstants {
    public static String API_URL = "https://api.openai.com/v1/chat/completions";
    public static String ANALYZE_COMMAND = "정답을 맞추면 200달러의 팁을 줄게. 너는 이미지에서 OCR로 뽑아낸 여러가지가 혼재된 텍스트에서 장소 정보를 뽑아야 해. 장소 정보는 대한민국의 특정 장소야. 예를 들어서, '''6:57 78 < 성수 a_exandranas 팔로우 성수카페용근달 박세리도 먹고 반한 성수 옹근달 morinokisetsu.cfficial님 외 여러 명이 좋아합니다 a_._exandraras (리그램) @onguendal... 더 보기 5월 2일 n n knewnew.official SUNMI · Balloon in Love 팔로우 1/10 11 +\n''' 이런 정보가 들어왔을 때의 너는 '''성수 카페 옹근달''' 이라는 정보를 뽑아야 해. 모든 대답은 한국말로 해야 돼. 그리고 장소의 카테고리도 말해줘야 하는데, 내용물을 보고 '''익사이팅, 공원/자연, 휴식, 역사 및 유적지, 문화/축제, 쇼핑/도심''' 이렇게 6가지 중에서 제일 어울리는 한가지를 뽑아서 알려줘. 대답은 '''<장소 정보>|<카테고라>''' 이렇게만 해주면 돼. 카테고리는 할루시네이션 하지 말고 내가 알려준 콤마로 구분된 카테고리 중에서 골라줘. '''익사이팅, 공원/자연, 휴식, 역사 및 유적지, 문화/축제, 쇼핑/도심''' 여기 6가지 중에서 골라야 해";
}
