# How to Build an AI Second Brain for Marketing With Obsidian and Claude Code

*Published: 2026-07-28*

A second brain is a folder of plain text files that gives your AI assistant persistent memory of your projects, your voice, and how you work, so it stops starting from zero in every new conversation.

Marketers are being asked to do more with the same headcount. One marketer with AI is increasingly expected to ship the output of three.

We ran a hands-on workshop inside our AI Native Marketer program to address this new reality: a structured knowledge base, built in the free note-taking app Obsidian and operated by Claude Code, that any AI can read at the start of every session.

This guide walks through the full system: why AI forgets, the folder structure that fixes it, and the five steps to build your own version in under an hour.

## TL;DR

- An AI second brain for marketing is a persistent knowledge base that stores project context, brand voice, frameworks, and previous decisions so AI tools can reuse them across sessions.
- Build it around seven components: Projects, Wiki, Frameworks, Raw, Drafts, Daily Logs, and a `CLAUDE.md` file that tells Claude Code how to use the knowledge base.
- Daily logs preserve decisions between AI sessions. Save what was completed, what changed, and what's next, then configure Claude Code to retrieve relevant logs when work resumes.
- Start with projects, not agents. Set up the knowledge base and daily logs before adding agents for content production, SEO updates, or quality assurance.
- Obsidian isn't required. The same Markdown-based system can work in other folders or repositories. Obsidian helps organize linked notes; GitHub adds version control for teams.

## What is a second brain for marketers?

An AI second brain is a structured, queryable knowledge base built outside the chat window. It holds your company context, project history, and reusable frameworks so an AI tool can reference it instead of starting from zero every session. It's infrastructure: a queryable vault of your company notes that a large language model can pull from on demand, so context builds up over time instead of resetting after every conversation.

## How do you build an AI second brain for marketing?

You build an AI second brain by creating a structured knowledge base, adding your project context and writing examples, connecting an AI tool, and setting up a process to save what happens between sessions.

You don't need 50 agents or a complicated automation setup to get started. The first version can be up and running in under an hour.

One principle before the steps: this system stays human-first. The AI reads real data, surfaces what matters, and suggests. You make the decisions.

### Step 1: Create your Obsidian vault and CLAUDE.md file

Start by creating a vault in Obsidian, the free note-taking app that stores everything as plain Markdown files.

Create six folders: `Projects/`, `Wiki/`, `Frameworks/`, `Raw/`, `Drafts/`, and `Daily Logs/`.

Then add a `CLAUDE.md` file at the root. This tells Claude Code who you are, how the folders are organized, where to find important context, and which rules it needs to follow.

Keep the instructions specific. "Never overwrite an existing note without asking" is more useful than "be careful with my files."

### Step 2: Add your active marketing projects

Start with the three to five projects you're actually working on. Create one Markdown file per project inside `Projects/`.

Each file should contain the goal, relevant stakeholders, current status, important decisions, and next steps. Link to any supporting research or documents the AI might need.

Don't spend hours importing every file you've ever created. Start with what's active. Everything else in the system exists to support those projects.

### Step 3: Add your brand voice and reference material

Your `Wiki/` folder holds the information you want AI tools to use repeatedly: brand guidelines, customer personas, positioning, and examples of your best content.

Add five to ten real examples per format, such as blog posts, newsletters, or LinkedIn posts. These give Claude something concrete to work from rather than defaulting to generic AI writing.

The same principle applies to `Frameworks/`. If your team has a specific way of evaluating campaigns, writing briefs, or reviewing content, document it. Otherwise, the AI fills in those gaps itself.

### Step 4: Connect Claude Code to your knowledge base

Open the vault folder in Claude Code and make sure it can access your `CLAUDE.md` instructions and project files.

Start with a simple test. Ask Claude to summarize one active project, identify its latest decisions, and suggest the next action using the information already in the vault.

Check the response against the original files. If it's missing important context, fix the instructions or file structure before adding more complexity.

You can connect external tools such as your calendar or email later, where your setup supports them. They aren't required to get the basic system working.

### Step 5: Set up daily logs before building agents

The daily log is what stops your second brain from becoming another folder you forget to update.

At the end of each working session, have Claude create a dated Markdown file recording what was completed, decisions made, outstanding questions, and what needs to happen next.

At the start of the next session, have it read the relevant recent logs alongside the active project files.

You can make that process repeatable through your instructions or a dedicated skill. It won't happen simply because you've created a `Daily Logs/` folder.

Get this working consistently before adding agents. Memory compounds. Agents built on top of no context don't.

### What can you actually do with an AI second brain?

Take a blog-updating agent. Instead of asking it to rewrite an old article from scratch, it can read the project brief in `Projects/`, pull writing examples from `Wiki/`, follow your editorial rules, and check previous decisions in `Daily Logs/`.

The agent has the context to work from. You still check the research, review the changes, and approve what gets published.

That's the difference between giving AI another prompt and building a system it can work inside.

## How should you structure an AI second brain?

A useful AI second brain separates active projects, reference knowledge, reusable frameworks, source material, drafts, and daily logs. A `CLAUDE.md` file explains how the folders connect and what Claude Code should retrieve when starting a task.

The structure we teach in the workshop comes from two sources.

Andrej Karpathy, a founding member of OpenAI, popularized a simple two-layer approach for personal knowledge bases: a Raw folder of source documents the AI reads but never modifies, and a Wiki folder of structured reference docs the AI builds and maintains from them.

Kieran Flanagan, HubSpot's SVP of Agentic GTM & Systems (previously its SVP of Marketing), extended that structure when he stepped into a role overseeing 400+ people and needed to onboard himself onto dozens of unfamiliar projects at once. He documented the expanded system on LinkedIn and Substack, adding project files, framework notes, and daily logs on top of Karpathy's original two layers.

We adapted his version for operators: people who execute the work directly rather than manage a large team. Here's the structure, folder by folder:

| Folder | Job |
|---|---|
| `Projects/` | One file per active project: goals, stakeholders, status |
| `Wiki/` | Durable reference: company info, tone of voice, examples, personas |
| `Frameworks/` | Your best practices and mental models, so the AI works your way instead of defaulting to its training |
| `Raw/` | Unstructured dumps and random ideas, processed on demand |
| `Drafts/` | Everything the AI produces: emails, posts, briefs |
| `Daily Logs/` | Auto-generated session summaries, one per day |
| `CLAUDE.md` | The entry point: a root file explaining who you are, how you work, and how the whole knowledge base is organized |

The entire system is plain markdown, stored in a folder you own. No proprietary format, no vendor lock-in, and no dependency on any single AI tool continuing to support it.

## How do daily logs give AI persistent memory?

Daily logs give AI tools a persistent record of completed work, decisions, and next steps. When configured to save and retrieve these records, Claude Code can carry relevant context between sessions instead of relying entirely on the current conversation.

If you build only one part of this system, build the daily logs.

Every time a working session ends, the conversation gets summarized into a dated file: what got done, decisions made, commitments made, and what carries forward to tomorrow. Every new session starts by reading the recent logs first. That single habit is what actually carries context between conversations, not a better prompt.

We tested this against the built-in memory features in Claude, and the real difference is control. With built-in memory, the model decides what's worth keeping, and you can review it but you didn't choose what got captured. With daily logs, the memory is a plain text file you can open, correct, and extend yourself. If the AI remembered something wrong, you edit the file directly. You can even reference a specific date: "on June 28th we decided X, pick that back up."

| | Built-in AI memory | Daily logs |
|---|---|---|
| What gets stored | The model decides | You decide, by writing the summary format |
| Visibility | Partial, reviewable in settings | Full, it's a text file |
| Editability | Limited | Fully editable |
| Dated recall | Not designed for it | Native ("on June 28th we decided X") |

The takeaway: daily logs don't win on convenience. Built-in memory requires zero setup. They win on transparency, and transparency is what makes an AI's recalled context trustworthy enough to act on without double-checking it every time.

## Do you need Obsidian to build an AI second brain?

No. An AI second brain can work with any organized collection of files an AI agent can access. Obsidian is useful for connecting notes, VS Code provides an editing environment, and GitHub adds version control and collaboration. The knowledge base matters more than the app.

Any AI agent that operates on files reads a folder of markdown the same way whether that folder lives inside Obsidian, VS Code, or a bare directory.

The difference shows up around day 30. Obsidian's core feature is the wikilink: typing `[[note name]]` inside any file automatically creates a connection to another note. Over time, an AI agent using the vault starts linking every new document to existing ones (a draft links to its project, the project links to its stakeholders and reference examples), and the whole knowledge base becomes more searchable and more useful the longer you use it. In a plain, unlinked folder, every output stays an isolated file no matter how many you accumulate.

The practical decision rule: Obsidian for a personal system one person owns and maintains. GitHub if a team needs to collaborate on the same knowledge base with real version control and change tracking. Neither tool makes the underlying AI smarter. They're both just different windows onto the same folder of files.

## Agents, skills, and commands: start from your projects

The most common mistake teams make once the folder structure exists is building 50 AI agents on day one. Don't. Start from your actual projects and work backwards from there.

Our own setup runs on three active projects, and therefore three agents:

- A copywriter that drafts newsletters, LinkedIn posts, and blog posts in our voice, grounded in real examples stored in the Wiki folder.
- A blog updater that revamps older posts for AI search visibility.
- A QA reviewer that fact-checks everything against real sources before publishing, because AI output ships with hallucinations if nobody checks it.

Notice what those three agents actually are: a copywriter, an editor, a reviewer. That maps directly to a shift we've heard described from inside a marketing team already operating this way. At a previous CXL workshop, Nick Christensen, head of growth at AppSumo, put it this way:

> "Being AI native means doing the reporting without a BI person, and building the campaign without a copywriter or a designer."

The job title stops being the unit of work. Shipping the work is. A second brain is where those roles live as agents grounded in real context, while a person stays the operator directing and judging the output, not just approving whatever the model produces first.

Two structures sit on top of the folders, and knowing when to use which matters:

- A **command** is a repeatable instruction that runs the same way every time: a morning brief, a weekly recap, "summarize my inbox." Fixed inputs, fixed steps, fixed output.
- An **agent with skills** handles a goal where the path varies. Writing a newsletter means researching, checking voice examples, drafting, and linking, and the specific steps differ every time. The agent owns the goal; skills hold the format-specific craft.

One sizing tip from the workshop's Q&A: keep individual skill files to roughly 100 to 150 lines. Beyond that length, models start struggling to apply a skill effectively.

## How do you keep an AI second brain accurate and secure?

An AI second brain needs regular maintenance. Outdated project decisions, conflicting instructions, and incorrect summaries don't become reliable just because an agent can retrieve them.

A few rules keep the system from turning into another messy folder:

- **Keep `Raw/` read-only.** Store original research and source documents separately from AI-generated summaries. An agent should reference them, not quietly rewrite them.
- **Date important decisions.** Daily logs should capture what changed, why it changed, and which project it affects.
- **Review `Wiki/` regularly.** Update outdated positioning, archive completed projects, and remove instructions that contradict each other.
- **Limit agent access.** Don't give every agent permission to read or modify every file. Separate confidential client information where appropriate.
- **Check what gets saved.** An incorrect AI summary can become tomorrow's incorrect context. Review important decisions before adding them to the permanent knowledge base.

One vault can work for multiple projects. But if you're managing clients with different confidentiality requirements, separate vaults or repositories with appropriate access permissions may be safer than keeping everything in different folders.

Memory compounds. So do mistakes. The system needs a way to catch them.

## Frequently asked questions

### What's the difference between an AI second brain and a knowledge base?

A knowledge base stores information. An AI second brain adds a workflow for retrieving, applying, and updating that information across AI sessions. The difference is whether the knowledge actually gets used.

### Can you build an AI second brain without coding?

Yes. You can create the folders and Markdown files manually in Obsidian. Claude Code helps automate retrieval, summaries, and updates, but the knowledge base itself doesn't require programming. Using Claude Code may require some command-line setup.

### What should you put in a CLAUDE.md file?

Include your working rules, project structure, quality standards, and instructions for finding relevant files. Keep detailed research, writing examples, and project history in their own folders rather than loading everything into one instruction file.

### Does an AI second brain replace Claude's built-in memory?

No. A file-based second brain complements built-in memory by giving you control over project documents, decisions, and daily logs. Claude Code also has automatic memory, but a separate knowledge base lets you organize context around your actual workflows.

### Should marketing teams share one AI second brain?

Teams can share a knowledge base containing approved positioning, brand voice, frameworks, and project context. But permissions matter. Separate folders aren't security boundaries, so confidential client information may need separate vaults or repositories with controlled access.

## Where most marketers stand today

Our 2026 AI maturity benchmark found that, among marketers who completed CXL's assessment, 57% were AI-assisted, 34% were AI-integrated, and just 9% reached AI-native capability. In operations specifically (workflows, automations, agents), 90% are still at the assisted stage and just 2% have reached native capability. Across every category measured, how often marketers use AI far outpaced how well they used it. Everyone is prompting. Few are building systems.

The same gap showed up in our research into how B2B marketers are using AI. Teams are getting comfortable with AI-assisted production, but building agents and repeatable workflows is a different skill.

If you want to build the system yourself, CXL's Build your personal AI operating system course walks through setting up the project library, writing `CLAUDE.md` instructions, creating reusable commands, and maintaining daily context. Once that's running, SEO automation with Claude Code shows how to apply the same approach to content research, blog updates, and recurring SEO workflows using real performance data. Start with the knowledge base. Build the agents once you know what they need to work from.

---

**Author:** Tarek Reslan — Tarek is Content Lead at CXL, where he currently explores and experiments with how AI can enhance marketing across multiple domains. From building automated content workflows to improving audience engagement and lead generation, he's focused on pushing practical, real-world applications of AI in marketing.