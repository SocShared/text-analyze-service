# Microservice TextAnalizer

## API
 -GET: /api/v1/users/{systemUserId}/text/extract_key_words
  Извлечение ключевых слов из текста
 request:
```json
	{
		"text": "<String: полный текст>"
	}
```
 response: 200
```json
	{
		"keyWords": [{
			"pharse": "<String: фраза>",
			"score": "<Integer: оценка того, на сколько хорошо фраза подходит>"
		}]//Список должен быть отсортирован по убыванию score
	}
```
 -GET: /api/v1/users/{systemUserId}/text/extract_target_phrases
  Извлечение целевых слов, заданные пользователем
  request:
```json
	{
		"text": "<String: полный текст>"
	}
```
 response: 200
```json
	{
		"targetPhrases": ["<String: Целевая фраза>"]
	}
```
-POST: /api/v1/users/{systemUserId}/text/extract_target_phrases
 Добавление целевой фразы, которая будет извлекаться при запросе на извлечение
 request:
```json
	{
		"targetPhrases": ["<String: Целевая фраза>"]
	}
```
 response: 200