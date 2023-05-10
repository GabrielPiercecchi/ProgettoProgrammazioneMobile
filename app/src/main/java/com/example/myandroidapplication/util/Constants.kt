package com.example.myandroidapplication.util

class Constants {
    companion object{
        const val PLAYERS_URL = "https://api.clashofclans.com/v1/"
        const val LOCATIONS_URL = "https://api.clashofclans.com/v1/"
        const val API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZ" +
                "CI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhN" +
                "SJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6" +
                "Z2FtZWFwaSIsImp0aSI6IjdjNzRiNjY1LTEzNzEtNGFiMi1iNzA4" +
                "LTUyNTlhNDc3Y2RmMCIsImlhdCI6MTY4MTU0NTM3Niwic3ViIjoi" +
                "ZGV2ZWxvcGVyL2Y2MjQ0MWNkLTg1NGUtNmUwNC02ODZjLWIwOThi" +
                "YzhjNzQ4NSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7" +
                "InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90" +
                "dGxpbmcifSx7ImNpZHJzIjpbIjU0Ljg2LjUwLjEzOSJdLCJ0eXBl" +
                "IjoiY2xpZW50In1dfQ.utmyn4FUKMLa0tiWifCDuDgufFnRifcRlZ" +
                "2kkTnR-OSaNupz5D5jIWNzw75Hu9wFxr92kO8TmXu63RWlncilaQ"

        const val API_KEY_G = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZ" +
                "CI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2Nh" +
                "NSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbG" +
                "w6Z2FtZWFwaSIsImp0aSI6IjI1MGMyN2JjLWQyZTQtNGM1ZS04Z" +
                "jcwLTRhMjlmYjA3OTc2OSIsImlhdCI6MTY4MzUzMzM2Miwic3ViI" +
                "joiZGV2ZWxvcGVyLzI1MjUyOTRkLWRjNWQtYTcwOS0zYTVhLTg4N" +
                "jc3YWQ5M2E1ZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOl" +
                "t7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm" +
                "90dGxpbmcifSx7ImNpZHJzIjpbIjM3LjE2My4xMzQuMjUzIl0sInR" +
                "5cGUiOiJjbGllbnQifV19.D48cLgzwYs1uJbgmFZkyzN9dyPMeOO" +
                "AUOCqZ3C2hvLC5lg5qWpDsTtP4gXaPMdN0JXY7gm9d-ZDgfF1QM-HbOg"

        const val API_KEY_L = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiI" +
                "sImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJ" +
                "jNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiL" +
                "CJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6I" +
                "mYzZDQ3ZDE1LWRlZjktNGJhZi1hNjNlLWYzOGY5MDlk" +
                "ZmMwZCIsImlhdCI6MTY4MzUzNjI5NSwic3ViIjoiZGV" +
                "2ZWxvcGVyLzI1MjUyOTRkLWRjNWQtYTcwOS0zYTVhLTg" +
                "4Njc3YWQ5M2E1ZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJ" +
                "saW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmV" +
                "yIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjp" +
                "bIjE3Ni4yNDYuMTkuMTI2Il0sInR5cGUiOiJjbGllbnQ" +
                "ifV19.Kdorht1QRgDmnqLJCSiwco-zuttL-Qo6HdFu5R" +
                "l5_Nqztj6wedpawQPwyQX1_SkGxSuF4gK0NsBNWeRpocjx8A"
        const val API_KEY_Z = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImI1NThhOTEwLWQwOTMtNDA4Yy05NGQzLTkyYTZlNGU4MGIzNiIsImlhdCI6MTY4MzY1NTUxNCwic3ViIjoiZGV2ZWxvcGVyL2Y2MjQ0MWNkLTg1NGUtNmUwNC02ODZjLWIwOThiYzhjNzQ4NSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3Ni4yNDYuMzkuMjE4Il0sInR5cGUiOiJjbGllbnQifV19.tfACewUcuCfXeuGN-TZde_t-Y02zcSELi4PlgDlb_WVUoALZoI6ZBlWMcqcQ6TVttIuaDeGQ3T-7K5Ct_TNyYw"
        const val API_KEY_Q = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6Ijc4ZDc4ZGNiLTBkMGEtNGJhMS04OTc2LTMwNDFjOGY4ODA4MCIsImlhdCI6MTY4MzcwOTcyMCwic3ViIjoiZGV2ZWxvcGVyL2Y2MjQ0MWNkLTg1NGUtNmUwNC02ODZjLWIwOThiYzhjNzQ4NSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3Ni4yNDYuMjYuMTgyIl0sInR5cGUiOiJjbGllbnQifV19.S92BXxWnIj7W0QxlEyKOGFoAQB5C3OyDKz_WBBuX7MedegFZxU7u3h89FX1MwHmj6TN3v5ij8Ka1iwYy4z6eBg"
        const val API_KEY_J = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImJmMzAxMmY2LTU4ODEtNDBhMS05NjRlLTg4OTc1NmM0ZWE3MyIsImlhdCI6MTY4MzcwOTg4OSwic3ViIjoiZGV2ZWxvcGVyL2Y2MjQ0MWNkLTg1NGUtNmUwNC02ODZjLWIwOThiYzhjNzQ4NSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjUuOTAuMTAxLjI0OSJdLCJ0eXBlIjoiY2xpZW50In1dfQ.PbvEUUU9fRyqthB74lHNOYsBp1P6E1GtNMSM4RVteTXVmBIBUfCwSgaODZM95VOV_uHlygL0oaWtzBQclcP3gQ"
    }
}