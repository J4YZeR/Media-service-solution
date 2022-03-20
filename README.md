# Media service task solution
This is the solution for media service creation task.

## Solution description
All requirements were met.  
No additional tables or columns are required to be present in DB.
Used configuration can be found in `pom.xml` and `application.properties`.

## API
### Write media
Persists media to database  
**URL:** `/medias`  
**Method:** `POST`  
**Request body:**

```json
{
    "id": "[string (36 chars)]",
    "type": "[IMAGE|VIDEO]",
    "url": "[string (0 to 255 chars)|null]"
}
```
*Note:* If a media with the same `id` exists in the system then all other fields should be
equal to the corresponding fields of already existing media.

*Data example:*
```json
{
    "id": "111111111111111111111111111111111111",
    "type": "VIDEO",
    "url": "http://example.com/video/1"
}
```
**Success Response:**  
* Status code: `200`  
* Condition:  
  * Media data is valid and was successfully persisted to database.  
If `type` is `VIDEO` and `url` is not empty then the process of 
  duration calculation for this video media was initiated.  
  
**Error Response**  
* Status code: `400`
* Conditions:
  * Given data is not in the correct format.  
  * Given data is in the correct format but media with the same `id` already
    exists in the system and at least one of given fields is not equal to the corresponding
    field of already existing media.
    
### Get media
Retrieves media with required id from the system  
**URL:** `/medias/:id`  
**Method:** `GET`  
**Path params:**   
*Required:*
* id
  * Type: `string`
  * Description: The id of required media.
    
**Success Response:**  
* Status code: `200`
* Condition: 
  * Media with given id was successfully found and returned.
* Content examples:
```json
{
  "id": "111111111111111111111111111111111111",
  "type": "VIDEO",
  "url": "http://example.com/video/1",
  "duration": 30
}
```
```json
{
  "id": "111111111111111111111111111111111111",
  "type": "VIDEO",
  "url": null,
  "duration": null
}
```
```json
{
  "id": "111111111111111111111111111111111111",
  "type": "IMAGE",
  "url": "http://example.com/image/1",
  "duration": null
}
```
 * *Note:*   
   If returned content has `type` equals `VIDEO`, `url` **not** `null` and `duration` 
   **is** `null` then this means that the process of duration calculation for this video
   was started but has not completed yet.  
   If returned content has `type` equals `VIDEO`, `url` **not** `null` and `duration`
   is **not** `null` then this means that the process of duration calculation has completed.  
   If `duration` field is `null` for the same video for a long period of time then this probably means that duration
   cannot be computed.
   
**Error Response:**  
* Status: `404`
* Condition:
  * Media with given id not found.
    
## Configuration
### Database
Url and credentials to postgres database which contains all necessary tables
should be provided in `application.properties` file.


