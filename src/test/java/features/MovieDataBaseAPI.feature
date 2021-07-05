#Author Rohith Ravi
@movieDataBaseAPI @api
Feature: Validate the MovieDatBaseAPI

  Background:
    Given I make REST service headers with the below fields
      | Accept           | Content-Type     |
      | application/json | application/json |

  @movieDataBaseAPI1 @regression
  Scenario Outline: Validate 200 status for get API
      When user calls "getTopRatedMovie" with "GET" http request and param "?api_key=85ba215c6213fae153008201c655c08b&language=<language>&page=<page>"
      And I should get the response code as "200"
      And "page" in response body is "<page>"
    Examples:
      | language | page |
      | en       | 1    |
      | hi       | 20   |

  @movieDataBaseAPI2 @regression
  Scenario Outline: Validate 401 status for get API
    When user calls "getTopRatedMovie" with "GET" http request and param "?api_key=<apiKey>"
    And I should get the response code as "401"
    And "status_message" in response body is "Invalid API key: You must be granted a valid key."

  Examples:
    | apiKey                        |
    | 85ba215c6213fae153008201c655c |
    |                               |
    | asda1q23123asawrq1231         |

  @movieDataBaseAPI3 @regression
  Scenario Outline: Rate a movie and validate the status
    When I read the message Body from the file "movieRating"
    And I update request message body with the below details
      | value   |
      | 8       |
    And I get the guest session id using api key "<apiKey>"
    When user calls "rateMovie" with "POST" http request and param "/<movieId>/rating?api_key=<apiKey>"
    And I should get the response code as "<statusCode>"
    And "status_message" in response body is "<message>"

    Examples:
      | apiKey                           | movieId | statusCode | message  |
      | 85ba215c6213fae153008201c655c08b | 19404   | 201        | Success. |

  @movieDataBaseAPI4 @regression
  Scenario Outline: verify the 401 status by not providing the guest session id
    When I read the message Body from the file "movieRating"
    And I update request message body with the below details
      | value   |
      | 8       |
    When user calls "rateMovie" with "POST" http request and param "/<movieId>/rating?api_key=<apiKey>"
    And I should get the response code as "<statusCode>"
    And "success" in response body is "<message>"

    Examples:
      | apiKey                           | movieId | statusCode | message |
      | 85ba215c6213fae153008201c655c08b | 19404   | 401        | false   |


