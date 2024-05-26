import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { expect, test } from "vitest";

import Sample from "./Sample";

test("Sample", async () => {
  const user = userEvent.setup();
  render(<Sample />);
  expect(screen.getByRole("heading")).toHaveTextContent("Hello, Guest");
  const button = screen.getByRole("button", { name: "Get User" });
  await user.click(button);
  // expect(await screen.findByText("Loading...")).toBeInTheDocument();
  expect(await screen.findByRole("heading")).toHaveTextContent(
    "Hello, John Maverick",
  );
});

test("snapshot test", () => {
  const { container } = render(<Sample />);
  expect(container).toMatchSnapshot();
});
