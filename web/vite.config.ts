/// <reference types="vitest" />
import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import tsConfigPaths from "vite-tsconfig-paths";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), tsConfigPaths()],
  test: {
    // globals: true,
    environment: "happy-dom",
    setupFiles: ["./vitest-setup.ts"],
    // スナップショットの依存先
    resolveSnapshotPath: (path, extension) => {
      return path.replace("/src/", "/__snapshots__/") + extension;
    },
  },
});
