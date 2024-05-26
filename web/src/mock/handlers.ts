import { http, HttpResponse } from "msw";

export const handlers = [
  http.get("http://dummyjson.com/users/1", () => {
    return HttpResponse.json({
      firstName: "John",
      lastName: "Maverick",
    });
  }),
];
